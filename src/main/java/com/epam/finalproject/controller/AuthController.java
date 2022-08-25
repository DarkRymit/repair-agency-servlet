package com.epam.finalproject.controller;

import com.epam.finalproject.framework.context.ApplicationEventPublisher;
import com.epam.finalproject.framework.security.Authentication;
import com.epam.finalproject.framework.security.AuthenticationException;
import com.epam.finalproject.framework.security.UserDetails;
import com.epam.finalproject.framework.security.UserDetailsService;
import com.epam.finalproject.framework.security.authentication.UsernamePasswordAuthenticationToken;
import com.epam.finalproject.framework.security.password.PasswordEncoder;
import com.epam.finalproject.framework.security.support.SecurityContext;
import com.epam.finalproject.framework.security.support.SecurityContextHolder;
import com.epam.finalproject.framework.security.web.SecurityContextRepository;
import com.epam.finalproject.framework.ui.RedirectAttributes;
import com.epam.finalproject.framework.web.WebHttpPair;
import com.epam.finalproject.framework.web.annotation.*;
import com.epam.finalproject.model.entity.PasswordResetToken;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.model.entity.VerificationToken;
import com.epam.finalproject.model.event.OnPasswordResetEvent;
import com.epam.finalproject.model.event.OnRegistrationCompleteEvent;
import com.epam.finalproject.request.NewPasswordRequest;
import com.epam.finalproject.request.PasswordResetRequest;
import com.epam.finalproject.request.SignUpRequest;
import com.epam.finalproject.service.PasswordResetTokenService;
import com.epam.finalproject.service.UserService;
import com.epam.finalproject.service.VerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AuthController.class);
    UserDetailsService userDetailsService;

    SecurityContextRepository securityContextRepository;

    PasswordEncoder passwordEncoder;

    UserService userService;

    VerificationTokenService verificationTokenService;

    PasswordResetTokenService passwordResetTokenService;

    ApplicationEventPublisher eventPublisher;

    public AuthController(UserDetailsService userDetailsService, SecurityContextRepository securityContextRepository,
            PasswordEncoder passwordEncoder, UserService userService, VerificationTokenService verificationTokenService,
            PasswordResetTokenService passwordResetTokenService, ApplicationEventPublisher eventPublisher) {
        this.userDetailsService = userDetailsService;
        this.securityContextRepository = securityContextRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
        this.passwordResetTokenService = passwordResetTokenService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/signup")
    String signUpPage() {
        return "signup";
    }


    @PostMapping("/signup")
    String signUp(@RequestObject SignUpRequest form, RedirectAttributes redirectedAttributes, HttpServletRequest request) {
        boolean isErrorsExists = false;
        if (userService.existsByUsername(form.getUsername())) {
            isErrorsExists = true;
            redirectedAttributes.addFlashAttribute("usernameError", "true");
        }
        if (userService.existsByEmail(form.getEmail())) {
            isErrorsExists = true;
            redirectedAttributes.addFlashAttribute("emailError", "true");
        }
        if (isErrorsExists) {
            return "redirect:/auth/signup";
        }
        User user = userService.signUpNewUserAccount(form);
        log.info("Created user:" + user.toString());
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), getAppUrl(request)));
        return "redirect:/auth/confirmRegister";
    }

    @GetMapping("/signin")
    String signInPage(@RequestParam(value = "error", required = false) String error, HttpServletRequest request) {
        if(error!=null)
            request.setAttribute("error","true");
        return "signin";
    }

    @PostMapping("/signin")
    public String signIn(HttpServletRequest request, HttpServletResponse response,@RequestParam("username") String username,@RequestParam("password") String password){

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new AuthenticationException("Password not match");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,userDetails.getAuthorities());

        securityContextRepository.saveContext(new SecurityContext(token),new WebHttpPair(request,response));

        return "redirect:/";
    }

    @GetMapping("/signout")
    String signOut(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
        }
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(null);
        SecurityContextHolder.resetContext();
        return "redirect:/";
    }

    @GetMapping("/confirmRegister")
    String confirmRegisterPage(){
        return "confirmRegister";
    }

    @GetMapping("/confirmRegister/{token}")
    String confirmRegisterTokenPage(@PathVariable("token") String token, HttpServletRequest request){
        request.setAttribute("token",token);
        return "confirmRegisterToken";
    }

    @PostMapping("/confirmRegister/{token}")
    String confirmRegisterToken(@PathVariable("token") String token){
        Optional<VerificationToken> optionalVerificationToken = verificationTokenService.findByToken(token);
        if (optionalVerificationToken.isEmpty()){
            return "redirect:/auth/confirmRegister?errorNoFound";
        }
        verificationTokenService.verifyByToken(optionalVerificationToken.orElseThrow());
        return "redirect:/auth/signin";
    }

    @GetMapping("/resetpassword")
    String resetPasswordPage(){
        return "resetPassword";
    }
    @PostMapping("/resetpassword")
    String resetPassword(HttpServletRequest request,@RequestObject PasswordResetRequest resetRequest){
        User user = userService.findByEmail(resetRequest.getEmail()).orElseThrow();
        eventPublisher.publishEvent(new OnPasswordResetEvent(user, request.getLocale(), getAppUrl(request)));
        return "redirect:/auth/resetpassword/confirm";
    }

    @GetMapping("/resetpassword/confirm")
    String resetPasswordConfirmPage(){
        return "resetPasswordConfirm";
    }

    @GetMapping("/resetpassword/confirm/{token}")
    String resetPasswordConfirmTokenPage(@PathVariable("token") String token){
        return "resetPasswordConfirmToken";
    }
    @PostMapping("/resetpassword/confirm/{token}")
    String resetPasswordConfirmToken(@PathVariable("token") String token,@RequestObject NewPasswordRequest newPasswordRequest){
        Optional<PasswordResetToken> optionalPasswordResetRequest = passwordResetTokenService.findByToken(token);
        if (optionalPasswordResetRequest.isEmpty()){
            return "redirect:/auth/resetpassword/confirm?errorNoFound";
        }
        PasswordResetToken passwordResetToken = optionalPasswordResetRequest.get();
        if (passwordResetTokenService.isExpired(passwordResetToken)){
            return "redirect:/auth/resetpassword/confirm?errorExp";
        }
        passwordResetTokenService.newPassword(passwordResetToken,newPasswordRequest);
        return "redirect:/auth/signin";
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}

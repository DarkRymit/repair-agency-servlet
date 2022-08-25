package com.epam.finalproject.framework.web.support;

import com.epam.finalproject.framework.ui.RedirectAttributes;

public class RedirectAttributesContext {
    private static final ThreadLocal<RedirectAttributes> redirectAttributesThreadLocal = new ThreadLocal<>();

    private RedirectAttributesContext() {
    }

    public static void resetRedirectAttributesContext() {
        redirectAttributesThreadLocal.remove();
    }
    public static void setRedirectAttributes( RedirectAttributes redirectAttributes) {
        if (redirectAttributes == null) {
            resetRedirectAttributesContext();
        } else {
            redirectAttributesThreadLocal.set(redirectAttributes);
        }
    }

    public static RedirectAttributes getRedirectAttributes() {
        return redirectAttributesThreadLocal.get();
    }


}

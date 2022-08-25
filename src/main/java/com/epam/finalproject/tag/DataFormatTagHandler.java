package com.epam.finalproject.tag;

import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DataFormatTagHandler  extends TagSupport {

    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

    private ZonedDateTime dateTime;

    public DataFormatTagHandler() {
       init();
    }

    @Override
    public int doStartTag()  {
        try {
            pageContext.getOut().write(dateTime.format(dateTimeFormatter));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SKIP_BODY;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public void release() {
        super.release();
        this.init();
    }
    private void init() {
        this.dateTime = null;
    }
}

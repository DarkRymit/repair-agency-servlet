package com.epam.finalproject.tag;

import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * The type Data format tag handler.
 */
public class DataFormatTagHandler  extends TagSupport {

    /**
     * The Date time formatter.
     */
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

    private ZonedDateTime dateTime;

    /**
     * Instantiates a new Data format tag handler.
     */
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

    /**
     * Sets date time.
     *
     * @param dateTime the date time
     */
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

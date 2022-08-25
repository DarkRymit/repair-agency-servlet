package com.epam.finalproject.framework.context;

import com.epam.finalproject.framework.beans.factory.ListableBeanFactory;

public interface ApplicationContext extends ListableBeanFactory,ApplicationEventPublisher {

    String getApplicationName();

    long getStartupDate();

}

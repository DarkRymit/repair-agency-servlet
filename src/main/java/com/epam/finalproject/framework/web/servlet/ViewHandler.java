package com.epam.finalproject.framework.web.servlet;

import com.epam.finalproject.framework.web.WebHttpPair;

public interface ViewHandler<T extends View> {
   void handle(T view, WebHttpPair pair);
}

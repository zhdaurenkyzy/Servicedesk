package com.epam.servicedesk.service;

import com.epam.servicedesk.exception.ConnectionException;
import com.epam.servicedesk.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public interface Service {
    void execute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws
            ServletException, IOException, ValidationException, SQLException, ConnectionException;
}

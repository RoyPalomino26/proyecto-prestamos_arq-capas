package dev.pitt.loans.loan_project.services.base.crud;

import dev.pitt.loans.loan_project.services.base.ServiceException;

import java.util.List;

public interface GenericGetAllService<T> {
    List<T> getAll() throws ServiceException;
}

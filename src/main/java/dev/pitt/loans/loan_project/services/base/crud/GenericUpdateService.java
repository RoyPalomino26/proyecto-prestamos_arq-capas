package dev.pitt.loans.loan_project.services.base.crud;


import dev.pitt.loans.loan_project.services.base.ServiceException;

public interface GenericUpdateService<D,R,ID> {

    R update(D d, ID id) throws ServiceException;
}

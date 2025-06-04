package dev.pitt.loans.loan_project.services.base.crud;

public interface GenericCrudService<D,R,ID> extends GenericSaveService<D, R>,
        GenericGetAllService<R>,
        GenericFindByIdService<R,ID>,
        GenericUpdateService<D,R,ID>,
        GenericDeleteService<ID> {
}

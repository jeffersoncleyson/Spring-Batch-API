package com.poc.springbatch.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

@NoRepositoryBean
public interface BaseRepository<S, K> extends PagingAndSortingRepository<S, K>, QueryByExampleExecutor<S> {
}

package com.example.shopcore.catalog.repository

import com.example.shopcore.catalog.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ProductRepository : JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {
    // Вызывать внутри транзакции: блокировки удерживаются до её завершения.
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from ProductEntity p where p.id in :ids order by p.id")
    fun findAllByIdForUpdate(@Param("ids") ids: Collection<Long>): List<ProductEntity>
}

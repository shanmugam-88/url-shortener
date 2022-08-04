package org.learn.shortener.repository

import org.learn.shortener.model.Url
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * UrlRepository class.
 */
@Repository
interface UrlRepository : JpaRepository<Url, Long>{

}
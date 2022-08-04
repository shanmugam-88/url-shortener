package org.learn.shortener.model

import java.time.LocalDate
import javax.persistence.*

@Entity
class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : Long = 0

    @Column(nullable = false)
    var url : String = ""

    var createdDate : LocalDate? = null

}
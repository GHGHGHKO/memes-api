package me.synology.memesapi.common.domain

import com.github.f4b6a3.ulid.UlidCreator
import org.hibernate.proxy.HibernateProxy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.Persistable
import java.io.Serializable
import java.time.LocalDateTime
import java.util.Objects
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import javax.persistence.PostLoad
import javax.persistence.PostPersist

@MappedSuperclass
abstract class BaseEntity : Persistable<UUID> {

    @Id
    @Column(columnDefinition = "uuid")
    private val id: UUID = UlidCreator.getMonotonicUlid().toUuid()

    @Column
    private val createDate: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    @Column
    private val updateDate: LocalDateTime = LocalDateTime.now()

    @Transient
    private var _isNew = true

    override fun getId(): UUID = id

    override fun isNew(): Boolean = _isNew

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        if (other !is HibernateProxy && this::class != other::class) {
            return false
        }
        return id == getIdentifier(other)
    }

    private fun getIdentifier(obj: Any): Serializable {
        return if (obj is HibernateProxy) {
            obj.hibernateLazyInitializer.identifier
        } else {
            (obj as BaseEntity).id
        }
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

    @PostPersist
    @PostLoad
    protected fun load() {
        _isNew = false
    }
}

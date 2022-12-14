package me.synology.memesapi.common.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.Table

@Entity
@Table(name = "user_master")
class UserMaster(
    email: String,
    password: String,
    nickName: String,
    createUser: String,
    updateUser: String,
    roles: MutableList<String>
) : BaseEntity(), UserDetails {

    @Column(unique = true, length = 100)
    val email: String = email

    @Column(length = 300)
    private var password: String = password
    override fun getPassword(): String = password

    @Column(length = 30)
    val nickName: String = nickName

    @Column(length = 100)
    val createUser: String = createUser

    @Column(length = 100)
    val updateUser: String = updateUser

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "user_master_roles",
        joinColumns = [JoinColumn(name = "user_master_id")])
    @Column
    val roles: MutableList<String> = roles
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles
            .map { _role -> SimpleGrantedAuthority(_role) }
            .toMutableList()
    }

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = false

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

}

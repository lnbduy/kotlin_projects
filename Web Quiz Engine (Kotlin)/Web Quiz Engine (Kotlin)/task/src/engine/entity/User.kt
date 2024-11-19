package engine.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0,

    @Column(nullable = false, unique = true)
    private val email: String,

    @Column(nullable = false)
    private val password: String,

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private val quizzes: MutableList<Quiz> = mutableListOf(),

    @Transient
    @ElementCollection(fetch = FetchType.EAGER)
    private val authorities: MutableCollection<SimpleGrantedAuthority>? = mutableListOf()
): UserDetails {
    override fun getAuthorities(): MutableCollection<SimpleGrantedAuthority>? = authorities

    override fun getPassword(): String = password

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}


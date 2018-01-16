package net.todo.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val applicationUserRepository: ApplicationUserRepository,
                             private val bCryptPasswordEncoder: BCryptPasswordEncoder) :
        UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        if(username != "test") throw UsernameNotFoundException(username)
        val user = ApplicationUser()
        user.username="test"
        user.password = bCryptPasswordEncoder.encode("password")
        return User(user.username,user.password,emptyList<GrantedAuthority>())

//        val applicationUser = applicationUserRepository.findByUsername(username) ?: throw UsernameNotFoundException(username)
//        return User(applicationUser.username, applicationUser.password, emptyList<GrantedAuthority>())
    }
}
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.elcom.vitalsign.auth;
//
//import com.elcom.vitalsign.model.Account;
//import java.util.Collection;
//import java.util.Collections;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
///**
// *
// * @author Admin
// */
//public class CustomAccountDetails implements UserDetails {
//
//    private Account account;
//
//    public CustomAccountDetails() {
//    }
//
//    public CustomAccountDetails(Account account) {
//        this.account = account;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + account.getRoleName()));
//    }
//
//    @Override
//    public String getPassword() {
//        return getAccount().getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return getAccount().getEmail();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    /**
//     * @return the user
//     */
//    public Account getAccount() {
//        return account;
//    }
//
//    /**
//     * @param user the user to set
//     */
//    public void setAccount(Account account) {
//        this.account = account;
//    }
//
//}

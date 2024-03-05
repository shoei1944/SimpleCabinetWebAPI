module simplecabinet {
    requires java.persistence;
    requires spring.web;
    requires spring.beans;
    requires spring.security.web;
    requires org.apache.tomcat.embed.core;
    requires spring.context;
    requires spring.boot.autoconfigure;
    requires spring.context.support;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires org.slf4j;
    requires spring.tx;
    requires spring.security.crypto;
    requires java.desktop;
    requires org.apache.commons.codec;
    requires stripe.java;
    requires jjwt.api;
    requires spring.security.core;
    requires spring.boot;
    requires spring.data.commons;
    requires java.transaction;
    requires totp;
    requires software.amazon.awssdk.auth;
    requires software.amazon.awssdk.core;
    requires software.amazon.awssdk.regions;
    requires software.amazon.awssdk.services.s3;
}
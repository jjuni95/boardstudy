/*
 * package com.study.service;
 * 
 * import org.springframework.cache.CacheManager; import
 * org.springframework.cache.annotation.EnableCaching; import
 * org.springframework.cache.ehcache.EhCacheCacheManager; import
 * org.springframework.cache.ehcache.EhCacheManagerFactoryBean; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.core.io.ClassPathResource;
 * 
 * @EnableCaching
 * 
 * @Configuration
 * 
 * public class MyCacheConfig {
 * 
 * @Bean
 * 
 * public CacheManager cacheManager() {
 * 
 * return new EhCacheCacheManager(ehCacheManager().getObject());
 * 
 * }
 * 
 * 
 * 
 * @Bean
 * 
 * public EhCacheManagerFactoryBean ehCacheManager() {
 * 
 * EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
 * 
 * bean.setConfigLocation(new ClassPathResource("ehcache.xml"));
 * 
 * bean.setShared(true);
 * 
 * }
 * 
 * }
 */
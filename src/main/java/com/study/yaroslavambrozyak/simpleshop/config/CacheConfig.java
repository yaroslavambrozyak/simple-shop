package com.study.yaroslavambrozyak.simpleshop.config;

import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

@Configuration
@EnableCaching
public class CacheConfig implements CachingConfigurer {

    @Bean(destroyMethod = "shutdown")
    public net.sf.ehcache.CacheManager ehCache(){
        net.sf.ehcache.config.Configuration configuration = new net.sf.ehcache.config.Configuration();
        configuration.addCache(createCacheConfiguration("rootCategory",10));
        configuration.addCache(createCacheConfiguration("subCategories",1000));
        return net.sf.ehcache.CacheManager.newInstance(configuration);
    }

    @Bean
    @Nullable
    @Override
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCache());
    }

    @Nullable
    @Override
    public CacheResolver cacheResolver() {
        return null;
    }

    @Nullable
    @Override
    public KeyGenerator keyGenerator() {
        return null;
    }


    @Bean
    @Nullable
    @Override
    public CacheErrorHandler errorHandler() {
        return null;
    }

    private CacheConfiguration createCacheConfiguration(String name, long maxEntries){
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setName(name);
        cacheConfiguration.setMaxEntriesLocalHeap(maxEntries);
        cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");
        return cacheConfiguration;
    }
}

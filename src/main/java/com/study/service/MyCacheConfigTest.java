/*
 * package com.study.service;
 * 
 * import org.junit.Test; import org.junit.runner.RunWith; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.cache.CacheManager; import
 * org.springframework.context.annotation.Import;
 * 
 * import net.sf.ehcache.Cache;
 * 
 * @RunWith(SpringRunner.class)
 * 
 * @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
 * 
 * @Import(MyCacheConfig.class)
 * 
 * public class MyCacheConfigTest {
 * 
 * @Autowired CacheManager cacheManager;
 * 
 * @Test public void testSample() {
 * 
 * Cache cache = cacheManager.getCache("testName");
 * 
 * cache.put("testKey", "testValue");
 * 
 * System.out.println("- cache=" + cache.get("testKey").get());
 * 
 * }
 * 
 * }
 */
/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.system;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Cache configuration intended for caches providing the JCache API. This configuration
 * creates the used cache for the application and enables statistics that become
 * accessible via JMX.
 */
@Configuration(proxyBeanMethods = false)
@EnableCaching
class CacheConfiguration {

	@Bean
	public CacheManager cacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager();

		// Owner cache configuration - higher traffic, longer TTL
		cacheManager.registerCustomCache("owners", Caffeine.newBuilder()
			.maximumSize(1000) // Assuming moderate user base
			.expireAfterWrite(30, TimeUnit.MINUTES)
			.recordStats()
			.build());

		// Pets cache configuration
		cacheManager.registerCustomCache("pets", Caffeine.newBuilder()
			.maximumSize(2000) // Typically more pets than owners
			.expireAfterWrite(30, TimeUnit.MINUTES)
			.recordStats()
			.build());

		// Vets cache configuration - lower traffic, longer TTL
		cacheManager.registerCustomCache("vets", Caffeine.newBuilder()
			.maximumSize(100) // Small number of vets
			.expireAfterWrite(2, TimeUnit.HOURS) // Less frequent updates
			.recordStats()
			.build());

		// Visits cache configuration
		cacheManager.registerCustomCache("visits", Caffeine.newBuilder()
			.maximumSize(5000) // Higher volume of visits
			.expireAfterWrite(15, TimeUnit.MINUTES) // More frequent updates
			.recordStats()
			.build());

		return cacheManager;
	}

}

/*
 * Copyright 2006-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.batch.item.database.support;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * @author Thomas Risberg
 * @author Michael Minella
 */
public class MySqlPagingQueryProviderTests extends AbstractSqlPagingQueryProviderTests {

	public MySqlPagingQueryProviderTests() {
		pagingQueryProvider = new MySqlPagingQueryProvider();
	}

	@Test
	@Override
	public void testGenerateFirstPageQuery() {
		String sql = "SELECT id, name, age FROM foo WHERE bar = 1 ORDER BY id ASC LIMIT 100";
		String s = pagingQueryProvider.generateFirstPageQuery(pageSize);
		assertEquals(sql, s);
	}

	@Test @Override
	public void testGenerateRemainingPagesQuery() {
		String sql = "SELECT id, name, age FROM foo WHERE bar = 1 AND id > ? ORDER BY id ASC LIMIT 100";
		String s = pagingQueryProvider.generateRemainingPagesQuery(pageSize);
		assertEquals(sql, s);
	}

	@Test @Override
	public void testGenerateJumpToItemQuery() {
		String sql = "SELECT id AS SORT_KEY FROM foo WHERE bar = 1 ORDER BY id ASC LIMIT 99, 1";
		String s = pagingQueryProvider.generateJumpToItemQuery(145, pageSize);
		assertEquals(sql, s);
	}
	
	@Test @Override
	public void testGenerateJumpToItemQueryForFirstPage() {
		String sql = "SELECT id AS SORT_KEY FROM foo WHERE bar = 1 ORDER BY id ASC LIMIT 0, 1";
		String s = pagingQueryProvider.generateJumpToItemQuery(45, pageSize);
		assertEquals(sql, s);
	}

	@Override
	@Test
	public void testGenerateFirstPageQueryWithGroupBy() {
		pagingQueryProvider.setGroupClause("dep");
		String sql = "SELECT id, name, age FROM foo WHERE bar = 1 GROUP BY dep ORDER BY id ASC LIMIT 100";
		String s = pagingQueryProvider.generateFirstPageQuery(pageSize);
		assertEquals(sql, s);
	}

	@Override
	@Test
	public void testGenerateRemainingPagesQueryWithGroupBy() {
		pagingQueryProvider.setGroupClause("dep");
		String sql = "SELECT id, name, age FROM foo WHERE bar = 1 AND id > ? GROUP BY dep ORDER BY id ASC LIMIT 100";
		String s = pagingQueryProvider.generateRemainingPagesQuery(pageSize);
		assertEquals(sql, s);
	}

	@Override
	@Test
	public void testGenerateJumpToItemQueryWithGroupBy() {
		pagingQueryProvider.setGroupClause("dep");
		String sql = "SELECT id AS SORT_KEY FROM foo WHERE bar = 1 GROUP BY dep ORDER BY id ASC LIMIT 99, 1";
		String s = pagingQueryProvider.generateJumpToItemQuery(145, pageSize);
		assertEquals(sql, s);
	}

	@Override
	@Test
	public void testGenerateJumpToItemQueryForFirstPageWithGroupBy() {
		pagingQueryProvider.setGroupClause("dep");
		String sql = "SELECT id AS SORT_KEY FROM foo WHERE bar = 1 GROUP BY dep ORDER BY id ASC LIMIT 0, 1";
		String s = pagingQueryProvider.generateJumpToItemQuery(45, pageSize);
		assertEquals(sql, s);
	}
}

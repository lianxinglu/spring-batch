package org.springframework.batch.item.database.support;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * @author Thomas Risberg
 * @author Michael Minella
 */
public class PostgresPagingQueryProviderTests extends AbstractSqlPagingQueryProviderTests {

	public PostgresPagingQueryProviderTests() {
		pagingQueryProvider = new PostgresPagingQueryProvider();
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
		String sql = "SELECT id AS SORT_KEY FROM foo WHERE bar = 1 ORDER BY id ASC LIMIT 1 OFFSET 99";
		String s = pagingQueryProvider.generateJumpToItemQuery(145, pageSize);
		assertEquals("Wrong SQL for jump to", sql, s);
	}

	@Test @Override
	public void testGenerateJumpToItemQueryForFirstPage() {
		String sql = "SELECT id AS SORT_KEY FROM foo WHERE bar = 1 ORDER BY id ASC LIMIT 1 OFFSET 0";
		String s = pagingQueryProvider.generateJumpToItemQuery(45, pageSize);
		assertEquals("Wrong SQL for first page", sql, s);
	}

	@Override
	@Test
	public void testGenerateFirstPageQueryWithGroupBy() {
		pagingQueryProvider.setGroupClause("id, dep");
		String sql = "SELECT id, name, age FROM foo WHERE bar = 1 GROUP BY id, dep ORDER BY id ASC LIMIT 100";
		String s = pagingQueryProvider.generateFirstPageQuery(pageSize);
		assertEquals(sql, s);
	}

	@Override
	@Test
	public void testGenerateRemainingPagesQueryWithGroupBy() {
		pagingQueryProvider.setGroupClause("id, dep");
		String sql = "SELECT id, name, age FROM foo WHERE bar = 1 AND id > ? GROUP BY id, dep ORDER BY id ASC LIMIT 100";
		String s = pagingQueryProvider.generateRemainingPagesQuery(pageSize);
		assertEquals(sql, s);
	}

	@Override
	@Test
	public void testGenerateJumpToItemQueryWithGroupBy() {
		pagingQueryProvider.setGroupClause("id, dep");
		String sql = "SELECT id AS SORT_KEY FROM foo WHERE bar = 1 GROUP BY id, dep ORDER BY id ASC LIMIT 1 OFFSET 99";
		String s = pagingQueryProvider.generateJumpToItemQuery(145, pageSize);
		assertEquals(sql, s);
	}

	@Override
	@Test
	public void testGenerateJumpToItemQueryForFirstPageWithGroupBy() {
		pagingQueryProvider.setGroupClause("id, dep");
		String sql = "SELECT id AS SORT_KEY FROM foo WHERE bar = 1 GROUP BY id, dep ORDER BY id ASC LIMIT 1 OFFSET 0";
		String s = pagingQueryProvider.generateJumpToItemQuery(45, pageSize);
		assertEquals(sql, s);
	}
}

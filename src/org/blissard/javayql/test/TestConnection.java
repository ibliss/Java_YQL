package org.blissard.javayql.test;

import static org.junit.Assert.*;

import org.blissard.javayql.Query;
import org.blissard.javayql.QueryConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class TestConnection {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	protected void testSingleStockQuoteResult(JsonObject r, String symbol) {
		JsonElement e;
		assertNotNull(r);
		r = r.getAsJsonObject("query");
		assertNotNull(r);
		e = r.get("count");
		assertNotNull(e);
		assertEquals(1, e.getAsInt());
		r = r.getAsJsonObject("results");
		assertNotNull(r);
		r = r.getAsJsonObject("quote");
		assertNotNull(r);
		e = r.get("symbol");
		assertNotNull(e);
		assertEquals(symbol, e.getAsString());
	}

	@Test
	public void testObj() {
		String target = "BBY";
		Query q = new Query(QueryConfiguration.ALLTABLES);
		q.setQuery(	"SELECT * FROM "+QueryConfiguration.Tables.AllTables.Finance.quotes+
					" WHERE symbol = \""+target+"\"");
		q.run();
		JsonObject r = q.getResult();
		testSingleStockQuoteResult(r, target);
	}

	@Test
	public void testStatic() {
		String target = "AAPL";
		JsonObject r = Query.query(QueryConfiguration.ALLTABLES, 
				"SELECT * FROM "+QueryConfiguration.Tables.AllTables.Finance.quotes+
				" WHERE symbol = \""+target+"\"" );
		testSingleStockQuoteResult(r, target);
	}

}

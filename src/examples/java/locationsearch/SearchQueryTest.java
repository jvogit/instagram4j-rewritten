package locationsearch;

import org.junit.Assert;
import org.junit.Test;

import serialize.SerializeTestUtil;
import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.requests.locationsearch.LocationSearchRequest;
import com.github.instagram4j.instagram4j.responses.locationsearch.LocationSearchResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SearchQueryTest {
    @Test
    public void testName() throws Exception {
        IGClient client = SerializeTestUtil.getClientFromSerialize("igclient.ser", "cookie.ser");
        LocationSearchResponse response = new LocationSearchRequest(0d, 0d, "mcdonalds").execute(client);
        response.getVenues().forEach(v -> log.debug("{} : {}", v.getName(), v.getExternal_id()));
        Assert.assertEquals("ok", response.getStatus());
        log.debug("Success");
    }
}

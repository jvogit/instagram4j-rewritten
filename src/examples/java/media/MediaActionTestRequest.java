package media;

import org.junit.Assert;
import org.junit.Test;

import serialize.SerializeTestUtil;
import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.requests.feed.FeedUserReelMediaRequest;
import com.github.instagram4j.instagram4j.requests.media.MediaActionRequest;
import com.github.instagram4j.instagram4j.requests.media.MediaActionRequest.MediaAction;
import com.github.instagram4j.instagram4j.requests.media.MediaCommentRequest;
import com.github.instagram4j.instagram4j.requests.media.MediaEditRequest;
import com.github.instagram4j.instagram4j.requests.media.MediaGetCommentsRequest;
import com.github.instagram4j.instagram4j.requests.media.MediaGetLikersRequest;
import com.github.instagram4j.instagram4j.requests.media.MediaInfoRequest;
import com.github.instagram4j.instagram4j.requests.media.MediaSeenRequest;
import com.github.instagram4j.instagram4j.responses.IGResponse;
import com.github.instagram4j.instagram4j.responses.feed.FeedUserReelsMediaResponse;
import com.github.instagram4j.instagram4j.responses.feed.FeedUsersResponse;
import com.github.instagram4j.instagram4j.responses.media.MediaGetCommentsResponse;
import com.github.instagram4j.instagram4j.utils.IGUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MediaActionTestRequest {
    @Test
    public void testInfo() throws Exception {
        IGClient client = SerializeTestUtil.getClientFromSerialize("igclient.ser", "cookie.ser");
        IGResponse response = new MediaInfoRequest(IGUtils.fromCode("CClpx7qsYm-") + "").execute(client);
        Assert.assertEquals("ok", response.getStatus());
    }
    
    @Test
    public void testDelete() throws Exception {
        IGClient client = SerializeTestUtil.getClientFromSerialize("igclient.ser", "cookie.ser");
        IGResponse response = new MediaActionRequest("2342700818431830321_18428658", MediaAction.DELETE).execute(client);
        Assert.assertEquals("ok", response.getStatus());
    }
    
    @Test
    public void testSave() throws Exception {
        IGClient client = SerializeTestUtil.getClientFromSerialize("igclient.ser", "cookie.ser");
        IGResponse response = new MediaActionRequest("2351884457617569072_18428658", MediaAction.UNSAVE).execute(client);
        Assert.assertEquals("ok", response.getStatus());
    }
    
    @Test
    public void testEdit() throws Exception {
        IGClient client = SerializeTestUtil.getClientFromSerialize("igclient.ser", "cookie.ser");
        IGResponse response = new MediaEditRequest("2342700818431830321_18428658", "edited caption").execute(client);
        Assert.assertEquals("ok", response.getStatus());
    }
    
    @Test
    public void testSeen() throws Exception {
        IGClient client = SerializeTestUtil.getClientFromSerialize("igclient.ser", "cookie.ser");
        FeedUserReelsMediaResponse reels = new FeedUserReelMediaRequest(18428658l).execute(client);
        IGResponse response = new MediaSeenRequest(reels.getReel().getItems()).execute(client);
        Assert.assertEquals("ok", response.getStatus());
    }
    
    @Test
    public void testOnlyMe() throws Exception {
        IGClient client = SerializeTestUtil.getClientFromSerialize("igclient.ser", "cookie.ser");
        IGResponse response = new MediaActionRequest("2351884457617569072_18428658", MediaAction.UNDO_ONLY_ME).execute(client);
        Assert.assertEquals("ok", response.getStatus());
    }
    
    @Test
    public void testLike() throws Exception {
        IGClient client = SerializeTestUtil.getClientFromSerialize("igclient.ser", "cookie.ser");
        IGResponse response = new MediaActionRequest("2351884457617569072", MediaAction.ENABLE_COMMENTS).execute(client);
        Assert.assertEquals("ok", response.getStatus());
    }
    
    @Test
    public void testComment() throws Exception {
        IGClient client = SerializeTestUtil.getClientFromSerialize("igclient.ser", "cookie.ser");
        IGResponse response = new MediaCommentRequest("2351884457617569072", "Gangnam").execute(client);
        Assert.assertEquals("ok", response.getStatus());
    }
    
    @Test
    public void testComments() throws Exception {
        IGClient client = SerializeTestUtil.getClientFromSerialize("igclient.ser", "cookie.ser");
        MediaGetCommentsResponse response = new MediaGetCommentsRequest("2355325964336133761_18428658").execute(client);
        response.getComments().forEach(c -> log.debug("{} : {}", c.getPk(), c.getText()));
        response = new MediaGetCommentsRequest("2355325964336133761_18428658", response.getNext_max_id()).execute(client);
        response.getComments().forEach(c -> log.debug("{} : {}", c.getPk(), c.getText()));
        Assert.assertEquals("ok", response.getStatus());
    }
    
    @Test
    public void testLikers() throws Exception {
        IGClient client = SerializeTestUtil.getClientFromSerialize("igclient.ser", "cookie.ser");
        FeedUsersResponse response = new MediaGetLikersRequest("2355325964336133761_18428658").execute(client);
        log.debug(response.getUsers().size() + "");
        Assert.assertEquals("ok", response.getStatus());
    }
}

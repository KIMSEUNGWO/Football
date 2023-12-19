package football.start.allOfFootball.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RankService {

    private final RedisTemplate<String, Long> template;
    private final ZSetOperations<String, Long> setOperations;

    @Autowired
    public RankService(RedisTemplate<String, Long> template) {
        this.template = template;
        this.setOperations = template.opsForZSet();
    }

    public void updateRank(Long memberId, int score) {
        setOperations.add("rank", memberId, score);
    }

    public Set<ZSetOperations.TypedTuple<Long>> getGlobalRank() {
        return setOperations.reverseRangeWithScores("rank", 0, -1);
    }
    public Long getRank(Long memberId) {
        Long rankTemp = 0L;
        Double rank = setOperations.score("rank", memberId);
        Set<Long> rank1 = setOperations.reverseRangeByScore("rank", rank, rank, 0, 1);
        for (Long l : rank1) {
            rankTemp = setOperations.reverseRank("rank", l);
        }
        return rankTemp + 1;
//        return setOperations.reverseRank("rank", memberId) + 1;
    }
}

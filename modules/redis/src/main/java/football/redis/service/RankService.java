package football.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RankService {

    private final ZSetOperations<String, Long> setOperations;

    @Autowired
    public RankService(RedisTemplate<String, Long> template) {
        this.setOperations = template.opsForZSet();
    }

    public void updateRank(Long memberId, int score) {
        Double rank = setOperations.score("rank", memberId);

        // 스코어에 변동이 없을경우 업데이트를 하지 않음
        if (rank != null && rank == (double) score) {
            System.out.println("memberId = " + memberId + " : 순위 변동없음");
            return;
        }

        setOperations.add("rank", memberId, score);
    }

    public Set<ZSetOperations.TypedTuple<Long>> getGlobalRank() {
        return setOperations.reverseRangeWithScores("rank", 0, -1);
    }
    public Long getRank(Long memberId, int score) {
        Long rankTemp = 0L;
        Double rank = setOperations.score("rank", memberId);
        if (rank == null) {
            updateRank(memberId, score);
            rank = setOperations.score("rank", memberId);
        }
        Set<Long> rank1 = setOperations.reverseRangeByScore("rank", rank, rank, 0, 1);
        for (Long l : rank1) {
            rankTemp = setOperations.reverseRank("rank", l);
        }
        return rankTemp + 1;
//        return setOperations.reverseRank("rank", memberId) + 1;
    }
}

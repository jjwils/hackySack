package core.domain;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class MergeRequestAggregate {

    final List<MergeRequest> mergeRequests;

    public MergeRequestAggregate(List<MergeRequest> mergeRequests) {
        this.mergeRequests = mergeRequests;
    }

    public long cumulativeTime() {

        AtomicLong cumTime = new AtomicLong();

        mergeRequests.forEach(mergeRequest -> {
            cumTime.set(cumTime.get() + Duration.between(mergeRequest.openedAt(), mergeRequest.mergedAt()).toMinutes());
        });

        return cumTime.longValue();
    }


}

package org.zalando.nakadi.domain;

import java.util.Objects;

public class NakadiCursor implements Comparable<NakadiCursor> {

    private final Timeline timeline;
    private final String partition;
    // NO BEGIN HERE - only real offset!
    private final String offset;

    public NakadiCursor(
            final Timeline timeline,
            final String partition,
            final String offset) {
        this.timeline = timeline;
        this.partition = partition;
        this.offset = offset;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public String getTopic() {
        return timeline.getTopic();
    }

    public String getEventType() {
        return timeline.getEventType();
    }

    public String getPartition() {
        return partition;
    }

    public String getOffset() {
        return offset;
    }

    public EventTypePartition getEventTypePartition() {
        return new EventTypePartition(timeline.getEventType(), partition);
    }

    public TopicPartition getTopicPartition() {
        return new TopicPartition(timeline.getTopic(), partition);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NakadiCursor)) {
            return false;
        }

        final NakadiCursor that = (NakadiCursor) o;
        return Objects.equals(this.timeline, that.timeline)
                && Objects.equals(this.partition, that.partition)
                && Objects.equals(this.offset, that.offset);
    }

    // TODO: Remove method one subscriptions are transferred to use timelines.
    public NakadiCursor withOffset(final String offset) {
        return new NakadiCursor(timeline, partition, offset);
    }

    @Override
    public int hashCode() {
        int result = timeline.hashCode();
        result = 31 * result + partition.hashCode();
        result = 31 * result + offset.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "NakadiCursor{" +
                "partition='" + partition + '\'' +
                ", offset='" + offset + '\'' +
                ", timeline='" + timeline + '\'' +
                '}';
    }

    @Override
    public int compareTo(final NakadiCursor cursor) {
        final int orderCompareResult = Integer.compare(this.getTimeline().getOrder(), cursor.getTimeline().getOrder());
        if (orderCompareResult != 0) {
            return orderCompareResult;
        }
        return offset.compareTo(cursor.getOffset());
    }
}

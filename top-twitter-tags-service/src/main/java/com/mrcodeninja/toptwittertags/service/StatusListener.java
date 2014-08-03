package com.mrcodeninja.toptwittertags.service;

import com.mrcodeninja.toptwittertags.data.StringCountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import twitter4j.HashtagEntity;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;

/**
 * Twitter4J {@link twitter4j.StatusListener} implementation that currently only records inbound hash tags.
 *
 * @author mrcodeninja
 */
public class StatusListener implements twitter4j.StatusListener {
    private final Logger LOGGER = LogManager.getLogger(StatusListener.class);
    private final StringCountRepository repository;

    /**
     * Creates a new {@link StatusListener}.
     */
    public StatusListener(StringCountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onStatus(Status status) {
        HashtagEntity[] hashTagEntities = status.getHashtagEntities();
        if (hashTagEntities.length == 0) {
            return;
        }

        for (HashtagEntity hashtagEntity : hashTagEntities) {
            repository.add(hashtagEntity.getText());
        }
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        // NO-OP:  Don't care about deletion notices.
    }

    @Override
    public void onTrackLimitationNotice(int i) {
        // NO-OP:  Don't care about track limitation notices.
    }

    @Override
    public void onScrubGeo(long l, long l1) {
        // NO-OP:  Don't care about scrub geo.
    }

    @Override
    public void onStallWarning(StallWarning stallWarning) {
        LOGGER.warn(String.format("Stall warning (%s): %s", stallWarning.getCode(), stallWarning.getMessage()));
    }

    @Override
    public void onException(Exception e) {
        LOGGER.error(e.getMessage(), e);
    }
}

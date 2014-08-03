package com.mrcodeninja.toptwittertags.execution;

import com.mrcodeninja.toptwittertags.data.StringCountRepository;
import com.mrcodeninja.toptwittertags.service.StatusListener;
import com.mrcodeninja.toptwittertags.service.StringCountPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Driver application for Twitter hash tag collection.
 *
 * @author mrcodeninja
 */
public class TopTwitterTagsController {
    private static final Logger LOGGER = LogManager.getLogger(TopTwitterTagsController.class);

    /**
     * Main entry point.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        StringCountRepository repository = new StringCountRepository();
        startOutputThread(repository);
        startTwitterSampling(repository);
    }

    /**
     * Spins up a thread that attaches to the Twitter Sample Streaming API.
     *
     * @param repository the repository
     */
    private static void startTwitterSampling(StringCountRepository repository) {
        StatusListener listener = new StatusListener(repository);
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(listener);
        twitterStream.sample();
    }

    /**
     * Spins up a thread to output the top hash tag occurrences.
     *
     * @param repository the repository
     */
    private static void startOutputThread(StringCountRepository repository) {
        Properties properties = new Properties();
        try {
            properties.load(TopTwitterTagsController.class.getResourceAsStream("/toptwittertags.properties"));
        } catch (IOException e) {
            LOGGER.fatal("Failed to load properties file.", e);
            System.exit(-1);
        }
        long printCount = Long.valueOf(properties.getProperty("output.tag.count"));
        long pollDelay = Long.valueOf(properties.getProperty("sample.polling.time.seconds"));

        ScheduledExecutorService printService = Executors.newSingleThreadScheduledExecutor();

        StringCountPrinter stringCountPrinter = new StringCountPrinter(repository, printCount);
        printService.scheduleAtFixedRate(stringCountPrinter, pollDelay, pollDelay, TimeUnit.SECONDS);
    }
}

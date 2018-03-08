package com.example.dengxuejiu.lithonews;

import android.os.Handler;

import com.example.dengxuejiu.lithonews.model.Feed;
import com.example.dengxuejiu.lithonews.model.FeedData;
import com.example.dengxuejiu.lithonews.model.FeedModel;
import com.facebook.litho.EventHandler;

import java.util.ArrayList;
import java.util.Random;

import static com.example.dengxuejiu.lithonews.model.Feed.FeedType.AD_FEED;
import static com.example.dengxuejiu.lithonews.model.Feed.FeedType.FIFTH;
import static com.example.dengxuejiu.lithonews.model.Feed.FeedType.FOURTH;
import static com.example.dengxuejiu.lithonews.model.Feed.FeedType.NEWS_FEED;
import static com.example.dengxuejiu.lithonews.model.Feed.FeedType.PHOTO_FEED;

/**
 * Created by dengxuejiu on 2018/3/8.
 */

public class DataService {
    private Random r = new Random();

    private EventHandler<FeedModel> dataModelEventHandler;//传递数据的核心

    //注册 EventHandler ，service中通过该 dataModelEventHandler 发送数据
    public void registerLoadingEvent(EventHandler<FeedModel> dataModelEventHandler) {
        this.dataModelEventHandler = dataModelEventHandler;
    }

    public void unregisterLoadingEvent() {
        this.dataModelEventHandler = null;
    }

    public void fetch(final int start, final int count) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FeedModel feedModel = getData(start, count);
                dataModelEventHandler.dispatchEvent(feedModel);//发送数据
            }
        }, 2000);
    }

    //刷新操作
    public void refetch(final int start, final int count) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FeedModel feedModel = getData(start, count);
                dataModelEventHandler.dispatchEvent(feedModel);//传输数据用
            }
        }, 2000);
    }

    public FeedModel getData(int start, int count) {
        FeedModel feedModel = new FeedModel();
        feedModel.feeds = new ArrayList<>(count);
        for (int i = start; i < start + count; i++) {
            Feed feed = generateFeed(i);
            feedModel.feeds.add(feed);
        }
        return feedModel;
    }


    private Feed generateFeed(int i) {
        Feed feed = new Feed();
        feed.id = i;
        switch (i % 5) {
            case 0:
                feed.type = NEWS_FEED;
                break;
            case 1:
                feed.type = AD_FEED;
                break;
            case 2:
                feed.type = PHOTO_FEED;
                break;
            case 3:
                feed.type = FOURTH;
                break;
            case 4:
                feed.type = FIFTH;
                break;
            default:
                feed.type = NEWS_FEED;
                break;
        }
        feed.data = generateData(feed.type);
        return feed;
    }

    private FeedData generateData(Feed.FeedType type) {
        FeedData data = new FeedData();
        switch (type) {
            case NEWS_FEED:
                data.title = title[r.nextInt(title.length)];
                data.description = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu.";
                data.photos = new int[]{photos[r.nextInt(photos.length)]};
                data.like = false;
                data.likeCount = 51;
                data.commentCount = 19;
                return data;
            case PHOTO_FEED:
                data.title = title[r.nextInt(title.length)];
                data.description = "Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu.";
                data.photos = photos;
                data.like = false;
                data.likeCount = 32;
                data.commentCount = 27;
                return data;
            case AD_FEED:
                data.title = "Top Advertisement";
                data.description = "Buy 1 get 1. Limited period offer. Hurry";
                data.photos = new int[]{R.mipmap.ic_launcher};
                data.like = false;
                data.likeCount = 67;
                data.commentCount = 77;
                return data;
            case FOURTH:
            case FIFTH:
                return data;
            default:
                throw new IllegalStateException("Invalid FeedType " + type);
        }
    }

    public static final int[] photos = new int[]{
            R.drawable.sample_feed_img,
            R.drawable.sample_feed_img_2,
            R.drawable.sample_feed_img_3,
            R.drawable.sample_feed_img_4
    };
    public static final String[] title = new String[]{
            "Created upon forth there sea under. Creepeth beast.",
            "Grass grass Moveth sixth from sixth, spirit third to man.",
            "Night said days fly fill saying is. Divided own god.",
            "Replenish bearing Hath beginning he kind night form had, darkness.",
            "Appear night to Above, tree, every greater that from good.",
            "Under herb creeping, brought unto god that great replenish. Whose.",
            "Above green was grass. Kind subdue they're whales meat. You'll.",
            "One own signs without man, us fruit evening his green.",
            "Were fill creeping his heaven waters form, it Fish the.",
            "Two him moveth first man forth Bring their his yielding.",
            "Itself whales spirit third. Bearing forth, fruitful given living creeping.",
            "Them. Him seas you his to, you'll, made darkness darkness.",
            "Living greater form living winged that stars. Shall form for.",
            "Fruitful together his day she'd years our living waters. Lights.",
            "Fifth seed made hath forth thing. Doesn't sixth fill deep.",
            "Created green a greater under second moving brought made that.",
            "Light spirit kind, without also void open in. You they're."
    };
}

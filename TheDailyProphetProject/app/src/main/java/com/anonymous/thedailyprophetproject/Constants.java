package com.anonymous.thedailyprophetproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Constants {

    static List<CardDetails> cards;
    static HashMap<String, Integer> cardIndex;

    static{
        cards = new ArrayList<>();
        cards.add(new CardDetails("Piyush Pawar", "Team Member\nFinal Year B.Tech. I.T.\nVJTI", R.drawable.devs2, "card"));
        cards.add(new CardDetails("Yogesh Mangnaik", "Team Member\nFinal Year B.Tech. I.T.\nVJTI", R.drawable.yogesh, "yogesh"));
        cards.add(new CardDetails("Nisarg Mistry", "Team Member\nFinal Year B.Tech. I.T.\nVJTI", R.drawable.nisarg, "piyush"));
        cardIndex = new HashMap<>();
        cardIndex.put("piyushpawar", 1);
        cardIndex.put("yogeshmangnaik", 2);
        cardIndex.put("nisargmistry", 3);
    }
}

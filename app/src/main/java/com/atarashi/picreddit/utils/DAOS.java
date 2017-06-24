package com.atarashi.picreddit.utils;

import com.atarashi.picreddit.utils.redditjsonclass.Child;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.realm.Realm;

/**
 * Created by avneeshk on 4/14/2017.
 */

public class DAOS {
    Realm realm;
    public DAOS(Realm realm){
        this.realm=realm;
    }
    public void insert(final String gson,final String subredditname){
        Long count= realm.where(OfflineGson.class).count();
        if(count==0){
            final OfflineGson person = null;
            innerInsert(person,subredditname,gson);
        }
        else{
            final OfflineGson person = realm.where(OfflineGson.class).findFirst();
            innerInsert(person,subredditname,gson);

        }

    }

    public ArrayList<Child> getArray(String subrredditname,Gson gson) {
        ArrayList<Child> temp=null;

        final OfflineGson person = realm.where(OfflineGson.class).findFirst();
        Type listOfTestObject = new TypeToken<ArrayList<Child>>() {}.getType();
        Long count= realm.where(OfflineGson.class).count();
        if(count>0) {
            if (subrredditname == "alternativeart") {
                temp = gson.fromJson(person.getAlternativeart(), listOfTestObject);
            } else if (subrredditname == "pics") {
                temp = gson.fromJson(person.getPics(), listOfTestObject);

            } else if (subrredditname == "gifs") {
                temp = gson.fromJson(person.getGifs(), listOfTestObject);

            } else if (subrredditname == "adviceanimals") {
                temp = gson.fromJson(person.getAdviceanimals(), listOfTestObject);
            } else if (subrredditname == "cats") {
                temp = gson.fromJson(person.getCats(), listOfTestObject);

            } else if (subrredditname == "images") {
                temp = gson.fromJson(person.getImages(), listOfTestObject);
            } else if (subrredditname == "photoshopbattles") {
                temp = gson.fromJson(person.getPhotoshopbattles(), listOfTestObject);
            } else if (subrredditname == "hmmm") {
                temp = gson.fromJson(person.getHmmm(), listOfTestObject);
            } else if (subrredditname == "all") {
                temp = gson.fromJson(person.getAll(), listOfTestObject);
            } else if (subrredditname == "aww") {
                temp = gson.fromJson(person.getAww(), listOfTestObject);
            }
        }
        return temp;
    }

    private void innerInsert(final OfflineGson person,final String subredditname,final String gson){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // Add a person

                if(person!=null) {
                    if (subredditname == "alternativeart") {
                        person.setAlternativeart(gson);
                    }
                    if (subredditname == "pics") {
                        person.setPics(gson);
                    }
                    if (subredditname == "gifs") {
                        person.setGifs(gson);
                    }
                    if (subredditname == "adviceanimals") {
                        person.setAdviceanimals(gson);
                    }
                    if (subredditname == "cats") {
                        person.setCats(gson);
                    }
                    if (subredditname == "images") {
                        person.setImages(gson);
                    }
                    if (subredditname == "photoshopbattles") {
                        person.setPhotoshopbattles(gson);
                    }
                    if (subredditname == "hmmm") {
                        person.setHmmm(gson);
                    }
                    if (subredditname == "all") {
                        person.setAll(gson);
                    }
                    if (subredditname == "aww") {
                        person.setAww(gson);
                    }
                }
                else{
                    final OfflineGson newperson = realm.createObject(OfflineGson.class);
                    if (subredditname == "alternativeart") {
                        newperson.setAlternativeart(gson);
                    }
                    if (subredditname == "pics") {
                        newperson.setPics(gson);
                    }
                    if (subredditname == "gifs") {
                        newperson.setGifs(gson);
                    }
                    if (subredditname == "adviceanimals") {
                        newperson.setAdviceanimals(gson);
                    }
                    if (subredditname == "cats") {
                        newperson.setCats(gson);
                    }
                    if (subredditname == "images") {
                        newperson.setImages(gson);
                    }
                    if (subredditname == "photoshopbattles") {
                        newperson.setPhotoshopbattles(gson);
                    }
                    if (subredditname == "hmmm") {
                        newperson.setHmmm(gson);
                    }
                    if (subredditname == "all") {
                        newperson.setAll(gson);
                    }
                    if (subredditname == "aww") {
                        newperson.setAww(gson);
                    }
                }

            }
        });
    }

}

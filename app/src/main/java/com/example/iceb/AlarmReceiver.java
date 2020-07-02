package com.example.iceb;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class AlarmReceiver extends BroadcastReceiver{
    private static final String CHANNEL_ID = "com.singhajit.notificationDemo.channelId";
    public static String qt=quote();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {

        long when = System.currentTimeMillis();
        Intent notificationIntent1 = new Intent(context, Quote.class);
        notificationIntent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(Quote.class);
        stackBuilder.addNextIntent(notificationIntent1);

        PendingIntent pendingIntent1 = stackBuilder.getPendingIntent(200, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar rightNow = Calendar.getInstance();
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
        String s;
        if(currentHour>=0&&currentHour<=3)
        {
            s="Good Night!!";
        }else if(currentHour>=4&&currentHour<=11)
        {
            s="Good Morning!!";
        }
        else if(currentHour>=12&&currentHour<=16)
        {
            s="Good Afternoon!!";
        }
        else
        {
            if(currentHour<=20) {
                s = "Good Evening!!";
            }else
            {
                s="Good Night!!";
            }
        }

        Notification.Builder builder1 = new Notification.Builder(context);

         @SuppressLint("WrongConstant") Notification notification1 = builder1.setContentTitle(s)
                .setContentText(qt)
                .setStyle(new Notification.BigTextStyle().bigText(qt))
                .setTicker("New Message Alert!")
                .setSmallIcon(R.mipmap.ice)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent1).build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder1.setChannelId(CHANNEL_ID);
        }

        NotificationManager notificationManager1 = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_ID,
                    "NotificationM",
                    IMPORTANCE_DEFAULT
            );
           // assert notificationManager1 != null;
            notificationManager1.createNotificationChannel(channel1);
        }

       // assert notificationManager1 != null;
        notificationManager1.notify(200, notification1);
    }

    public static String quote()
    {
        ArrayList<String> quotes=new ArrayList<>();
        quotes.add("Tough times don’t last. Tough people do. – Robert H. Schuller");
        quotes.add("Keep going. Everything you need will come to you at the perfect time.");
        quotes.add("Keep going. Everything you need will come to you at the perfect ");
        quotes.add("You have to be at your strongest when you’re feeling at your weakest.");
        quotes.add("Never give up. Great things take time. Be patient.");
        quotes.add("It does not matter how slowly you go as long as you do not stop. – Confucius");
        quotes.add("You have to be at your strongest when you’re feeling at your weakest.");
        quotes.add("Courage is one step ahead of fear. – Coleman Young");
        quotes.add("If you feel like giving up, just look back on how far you are already.");
        quotes.add("Look in the mirror. That’s your competition.");
        quotes.add("Focus on your goal. Don’t look in any direction but ahead.");
        quotes.add("Everything you’ve ever wanted is on the other side of fear. — George Addair");
        quotes.add("Pain is temporary. Quitting lasts forever. – Lance Armstrong");
        quotes.add("The pain you feel today will be the strength you feel tomorrow. – Unknown");
        quotes.add("We must embrace pain and burn it as fuel for our journey. – Kenji Miyazawa");
        quotes.add("A problem is a chance for you to do your best. – Duke Ellington");
        quotes.add("Hard times don’t create heroes. It is during the hard times when the ‘hero’ within us is revealed. – Bob Riley");
        quotes.add("Remember it’s just a bad day, not a bad life.");
        quotes.add("Whatever is worrying you right now, forget about it. Take a deep breath, stay positive and know that things will get better. – Unknown");
        quotes.add("It’s not about perfect. It’s about effort. – Jillian Michaels");
        quotes.add("Believe you can and you’re halfway there. – Theodore Roosevelt");
        quotes.add("Challenges are what make life interesting. Overcoming them is what makes them meaningful.");
        quotes.add("You are so much more than what you are going through. – John Tew");
        quotes.add("Passion first and everything will fall into place. – Holly Holm");
        quotes.add("You don’t gain anything from stressing. Remember that");
        quotes.add("You have to be at your strongest when you’re feeling at your weakest.");
        quotes.add("Difficult roads always lead to beautiful destinations. – Zig Ziglar");
        quotes.add("Staying positive does not mean that things will turn out okay. Rather it is knowing that you will be okay no matter how things turn out. – Unknown");
        quotes.add("Success is what happens after you have survived all of your disappointments.");
        quotes.add("Goals may give focus, but dreams give power. – John Maxwell");
        quotes.add("Don’t wish it were easier. Wish you were better. ― Jim Rohn");
        quotes.add("Your mind is a powerful thing. When you fill it with positive thoughts, your life will start to change.");
        quotes.add("Hustle until you no longer have to introduce yourself.");
        quotes.add("Success is what happens after you have survived all of your disappointments.");
        quotes.add("You don’t always get what you wish for. But you always get what you work for.");
        quotes.add("You don’t find will power, you create it.");
        quotes.add("Once you choose hope, anything’s possible. – Christopher Reeve");
        quotes.add("Push yourself because no one else is going to do it.");
        quotes.add("You cannot fail at being yourself. – Wayne Dyer");
        quotes.add("Don’t wait for opportunity. Create it.");
        quotes.add("You are the only one who can limit your greatness. – Unknown");
        quotes.add("The first and greatest victory is to conquer self. – Plato");
        quotes.add("Results happen over time, not overnight. Work hard, stay consistent, and be patient.");
        quotes.add("With confidence you have won before you have started. – Marcus Garvey");
        quotes.add("Success is what comes after you stop making excuses. – Luis Galarza");
        quotes.add("You don’t want to look back and know you could have done better.");
        quotes.add("The moment you doubt whether you can fly, you cease for ever to be able to do it. – J.M. Barrie");
        quotes.add("Be so good they can’t ignore you. – Steve Martin");
        quotes.add("You will never always be motivated. You have to learn to be disciplined.");
        quotes.add("Believe in yourself! Have faith in your abilities! Without a humble but reasonable confidence in your own powers you cannot be successful or happy. – Norman Vincent Peale");
        quotes.add("When you know what you want, and want it bad enough, you’ll find a way to get it. – Jim Rohn");
        quotes.add("The best way to gain self-confidence is to do what you are afraid to do. ");
        quotes.add("Motivation is what gets you started. Habit is what keeps you going. ");
        quotes.add("Never stop trying. Never stop believing. Never give up. Your day will come.");
        quotes.add("Results happen over time, not overnight. Work hard, stay consistent, and be patient.");
        quotes.add("Our greatest glory is not in never falling, but in rising every time we fall");
        quotes.add("Success is what happens after you have survived all of your disappointments.");
        quotes.add("Don’t try to be perfect. Just try to be better than you were yesterday.");
        quotes.add("Start where you are. Use what you have. Do what you can");
        quotes.add("Don’t stop until you’re proud.");
        quotes.add("Strength does not come from physical capacity. It comes from an indomitable will. – Mahatma Gandhi");
        quotes.add("I’m not telling you it is going to be easy, I’m telling you it’s going to be worth it. ");
        quotes.add("If you want it, you’ll find a way. If you don’t, you’ll find an excuse.");
        quotes.add("Perseverance is the hard work you do after you get tired of doing the hard work you already did");
        quotes.add("It is during our darkest moments that we must focus to see the light");
        quotes.add("Perfection is not attainable, but if we chase perfection we can catch excellence. ");
        quotes.add("We may encounter many defeats but we must not be defeated.");
        quotes.add("Strength doesn’t come from what you can do. It comes from overcoming the things you once thought you couldn’t.");
        quotes.add("He who is not courageous enough to take risks will accomplish nothing in life");
        quotes.add("We don’t develop courage by being happy every day. We develop it by surviving difficult times and challenging adversity.");
        quotes.add("When you fear your struggles, your struggles consume you. When you face your struggles, you overcome them.");
        quotes.add("I learned that courage was not the absence of fear, but the triumph over it. The brave man is not he who does not feel afraid, but he who conquers that fear. – Nelson Mandela");
        quotes.add("Keep going. Everything you need will come to you at the perfect time.");
        quotes.add("Set a goal so big that you can’t achieve it until you grow into the person who can.");
        quotes.add("All our dreams can come true, if we have the courage to pursue them. ");
        quotes.add("The path to success is to take massive, determined action.");
        quotes.add("The only person you are destined to become is the person you decide to be");
        quotes.add("If you get tired, learn to rest, not quit.");
        quotes.add("Pessimism leads to weakness, optimism to power. ");
        quotes.add("If it doesn’t challenge you, it won’t change you.");
        quotes.add("The struggle you’re in today is developing the strength you need for tomorrow");
        quotes.add("Life is 10% what happens to you and 90% how you react to it");
        quotes.add("Believe in yourself. You are braver than you think, more talented than you know, and capable of more than you imagine");
        quotes.add("Strength is the product of struggle. You must do what others don’t to achieve what others wont.");
        quotes.add("Don’t think about what might go wrong. Think about what might go right.");
        quotes.add("Your hardest times often lead to the greatest moments of your life. Keep going. Tough situations build strong people in the end");
        quotes.add("You have to fight through some bad days to earn the best days of your life");
        quotes.add("Make the most of yourself….for that is all there is of you. ");
        quotes.add("It takes courage to grow up and become who you really are");
        quotes.add("Every next level of your life will demand a different version of you");
        quotes.add("Don’t let your fear decide your future");
        quotes.add("Do something today that your future self will thank you for.");
        quotes.add("You were put on this Earth to achieve your greatest self, to live out your purpose, and to do it courageously");
        quotes.add("The future belongs to those who believe in the beauty of their dreams.");
        quotes.add("Don’t downgrade your dream just to fit your reality. Upgrade your conviction to match your destiny.");
        quotes.add("Don’t be pushed around by the fears in your mind. Be led by the dreams in your heart.");
        quotes.add("Choosing to be positive and having a grateful attitude is going to determine how you’re going to live your life.");
        quotes.add("If you believe it will work out, you’ll see opportunities. If you believe it won’t, you will see obstacles.");
        quotes.add("Life is a process. We are a process. The universe is a process. ");
        quotes.add("Life shrinks or expands in proportion to one’s courage.");
        quotes.add("Shine like the whole universe is yours");
        quotes.add("When a new day begins, dare to smile gratefully.");
        quotes.add("Because of your smile, you make life more beautiful.");
        quotes.add("There are so many beautiful reasons to be happy today.");
        quotes.add("Rise up. Start fresh. See the bright opportunity in each new day.");
        quotes.add("Open your eyes and notice the beauty of this wonderful world.");
        quotes.add("Today is a beautiful day and I will attract good things into my life.");
        quotes.add("What ever you decide to do today, make sure it makes you happy");
        quotes.add("Spread love everywhere you go. Let no one ever come to you without leaving happier. – Mother Teresa");
        quotes.add("Wake up every morning with the thought that something wonderful is about to happen.");
        quotes.add("It’s a good day to be happy");
        quotes.add("Be you. Do you. For you");
        quotes.add("Another day another blessing.");
        quotes.add("Life works for my benefit.");
        quotes.add("Work hard and be nice to people");
        quotes.add("Self confidence is the best outfit");
        quotes.add("Choose kindness and laugh often.");
        quotes.add("Throw kindness around like confetti");
        quotes.add("A smile is the beauty of the soul");
        quotes.add("Everywhere you go, take a smile with you");
        quotes.add("What would you do today if you weren’t afraid?");
        quotes.add("A little progress each day adds up to big results.");
        quotes.add("The best way to predict your future is to create it. ");
        quotes.add("Take it all one day at a time and enjoy the journey");
        quotes.add("Push yourself because no one else is going to do it for you");
        quotes.add("The best way to make your dreams come true is to wake up");
        quotes.add("I know I have unlimited potential to create the life of my dreams.");
        quotes.add("Set a goal that makes you want to jump out of bed in the morning.");
        quotes.add("Keep going. Everything you need will come to you at the perfect time.");
        quotes.add("I am in charge of how I feel and today I am choosing happiness.");
        quotes.add("Every morning you have a new opportunity to become a happier version of yourself");
        quotes.add("Appreciate the people who make you smile");
        quotes.add("You’ve got to find people who love like you do");
        quotes.add("Surround yourself with people who get you.");
        quotes.add("Happiness is only real when shared. ");
        quotes.add("We are shaped and fashioned by those we love");
        quotes.add("Never stop believing in hope because miracles happen everyday");
        quotes.add("If you have only one smile in you, give it to the people you love");
        quotes.add("Good people are like candles; they burn themselves up to give others light");
        quotes.add("Surround yourself with only people who are going to lift you higher");
        quotes.add("Spend your life with people who make you smile, laugh, and feel loved");
        quotes.add("You came into the world to do something; So… do something! ");
        quotes.add("No matter how you feel, get up, dress up, show up and never give up.");
        quotes.add("Count your age by friends, not years. Count your life by smiles, not tears. ");
        quotes.add("The people who make you smile from just seeing them, those are my favorite people");
        quotes.add("Life is too short to waste your time on people who don’t respect, appreciate, and value you. ");
        quotes.add("Who you spend your time with will have a great impact on what kind of life you live. Spend time with the right people.");
        quotes.add("Let us be grateful to the people who make us happy; they are the charming gardeners who make our souls blossom");
        quotes.add("Spread love. Hug the people you care about and make sure they know that you care and appreciate them. Make it known to your friends and family that you love them.");
        quotes.add("Create healthy habits, not restrictions.");
        quotes.add("Your largest fear carries your greatest growth");
        quotes.add("Your life is a direct reflection of what you have worked for");
        quotes.add("Do something today that your future self will thank you fo");
        quotes.add("Train your mind to see the good in any situation");
        quotes.add("Stop doubting yourself. Work hard and make it happen");
        quotes.add("You have to be at your strongest when you’re feeling at your weakest.");
        quotes.add("I’ve failed over and over and over again in my life and that is why I succeed");
        quotes.add("Confidence is something you create in yourself by believing in who you are");
        quotes.add("The struggle you’re in today is developing the strength you need for tomorrow");
        quotes.add("When you know what you want, and want it bad enough, you’ll find a way to get it.");
        quotes.add("Look for something positive in each day, even if some days you have to look a little harder");
        quotes.add("It is never too late to be what you might have been");
        quotes.add("Excuses will always be there for you. Opportunity won’t");
        quotes.add("Create a vision that makes you wanna jump out of bed in the morning");
        quotes.add("If you don’t feel it, flee from it. Go where you are celebrated, not merely tolerated");
        Collections.shuffle(quotes);
        String q=quotes.get(20);






        return q;

    }
}

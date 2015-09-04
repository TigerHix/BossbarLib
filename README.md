# BossbarLib
A simple, clean, performant and object-oriented bossbar library for latest Spigot.

Another bossbar library? Seriously?
--------------
Yes, there have been a lot of libraries utilizing the bossbar to display messages. However, none of them satisfies my need. BarAPI did well in pre-1.8, but not anymore in the latest versions; and the replacements I have found would either display streams of annoying smoke particles from places to places, or random flickering and disappearances that happens a lot.

Okay, but why choose BossbarLib?
--------------
* It works.
* Almost unobservable smoke particles.
* The bossbar wouldn't disappear unless you request to do so.
* Performant and no flickerings. Unlike some libraries, BossbarLib just wouldn't spawn a new entity every time a new message is displayed.
* Clean, object-oriented code that a ten-year-old could understand.
* Can be used as a standalone plugin, or be shaded into your project.

Is BossbarLib perfect?
--------------
Unfortunately, not quite. Here are some worth-mentioning problems:
* Wither shields when the health is under 50%. This is completely client-side, and there are no ways to avoid it. There are some workarounds, though: you can make a custom resource pack with `entity/wither/wither.png`, `entity/wither/wither_armor.png`, ``entity/wither/invulnerable.png` set to a blank, transparent image, so that players wouldn't be able to see the goddamn wither even though it is rendered. The second workaround is easier: just never set the health of a bossbar under 50%. If you want to use the bossbar as a timer, make the message to indicate the time left instead. 
* Version dependent (currently, it supports Spigot 1.8.8 only). Bad news, the default implementation of BossbarLib optimizes the wither entity by extending `EntityMonster`, a class in NMS package - which means you have to update BossbarLib every time when there is a Minecraft version update. The good news is, unless Mojang have developed new glitches for the bossbar again, making BossbarLib up-to-date usually wouldn't take so long.

How do I add it to my project?
--------------
Simply add the following to your `pom.xml`.

    <repository>
      <id>tiger-repo</id>
      <url>http://repo.tigerhix.me/content/repositories/snapshots/</url>
    </repository>

    <dependency>
      <groupId>me.tigerhix.lib</groupId>
      <artifactId>bossbar</artifactId>
      <version>1.0.1-SNAPSHOT</version>
    </dependency>

And, you are good to go.

How do I use it?
--------------
First, you have to decide whether you use BossbarLib as a standalone plugin, or you just go shade it into your own plugin. For the latter case, you have to add following code to your onEnable():

```java
BossbarLib.setPluginInstance(this);
```

To let BossbarLib holds a reference to your plugin and hence able to schedule tasks, register events, etc.

Setting the bossbar of a player is simple:

```java
BossbarLib.getHandler().getBossbar(player).setMessage(ChatColor.BOLD + "I love cookies.").setPercentage(1f);
BossbarLib.getHandler().updateBossbar(player);
```

And of course, to clear the bossbar:

```java
BossbarLib.getHandler().clearBossbar(player);
```

Do note that, `getBossbar(Player)` will instantiate a bossbar for that player if the player does not have one. For determining whether or not a player has a bossbar displayed, please use:

```java
BossbarLib.getHandler().hasBossbar(player);
```

Get used of `BarAPI.setMessage(Player, String)`-alike methods?
--------------
If you are get used to those static helper classes which are widely used in other bossbar libraries, simply add this [gist](https://gist.github.com/TigerHix/fd594f354b64ae298ff6) to your project. Now you can do the following:

```java
BossbarHelper.has(Player);
BossbarHelper.getMessage(Player);
BossbarHelper.getPercentage(Player);
BossbarHelper.updateMessage(Player, String);
BossbarHelper.updatePercentage(Player, float);
BossbarHelper.updateAll(Player, String, float);
BossbarHelper.clear(Player);
```

License
--------------
BossbarLib is licensed under the [GNU Lesser General Public License (Version 3)](https://github.com/TigerHix/BossbarLib/blob/1.8/LICENSE).

package org.aprestos.labs.snippets.impl.data.clustering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.carrot2.clustering.lingo.LingoClusteringAlgorithm;
import org.carrot2.core.Controller;
import org.carrot2.core.ControllerFactory;
import org.carrot2.core.Document;
import org.carrot2.core.LanguageCode;
import org.carrot2.core.ProcessingResult;
import org.junit.BeforeClass;
import org.junit.Test;

public class Carrot2 {

    private static final List<Document> documents = new ArrayList<>();
   
    
    @Test
    public void test() {
	
	Controller controller = ControllerFactory.createSimple();
	
	ProcessingResult result = controller.process(documents, null, LingoClusteringAlgorithm.class);
	controller.dispose();
	System.out.println(result.toString());
    }
    
    @Test
    public void test_oneDoc() {
	
	Controller controller = ControllerFactory.createSimple();
	ProcessingResult result = controller.process(Arrays.asList(new Document("Korea crisis",  "U.S. President Donald Trump agreed to back billions of dollars in new weapons sales to South Korea after North Korea’s largest nuclear test, while in a push for harsher sanctions his ambassador to the United Nations said Kim Jong Un’s regime is “begging for war.” Hours later, the Seoul-based Asia Business Daily reported that Pyongyang was preparing to launch an intercontinental ballistic missile before Saturday. Japan plans to evacuate about 60,000 of its citizens in South Korea if the U.S. decides to strike the rogue nation, regardless of whether plans are made public, Nikkei reported, citing an unnamed government source. Russian President Vladimir Putin rejected U.S. calls for new sanctions against North Korea on Tuesday. Geopolitical strife is just one event risk facing global investors this September -- the cruelest month for U.S. equities -- with hawkish central-bank chatter and U.S. fiscal brinkmanship also looming.", 
		LanguageCode.ENGLISH)), null, LingoClusteringAlgorithm.class);
	controller.dispose();
	System.out.println(result.toString());
    }
    
    @BeforeClass
    public static void setup() {
	
	documents.add(new Document("Korea crisis",  "U.S. President Donald Trump agreed to back billions of dollars in new weapons sales to South Korea after North Korea’s largest nuclear test, while in a push for harsher sanctions his ambassador to the United Nations said Kim Jong Un’s regime is “begging for war.” Hours later, the Seoul-based Asia Business Daily reported that Pyongyang was preparing to launch an intercontinental ballistic missile before Saturday. Japan plans to evacuate about 60,000 of its citizens in South Korea if the U.S. decides to strike the rogue nation, regardless of whether plans are made public, Nikkei reported, citing an unnamed government source. Russian President Vladimir Putin rejected U.S. calls for new sanctions against North Korea on Tuesday. Geopolitical strife is just one event risk facing global investors this September -- the cruelest month for U.S. equities -- with hawkish central-bank chatter and U.S. fiscal brinkmanship also looming.", 
		LanguageCode.ENGLISH));
	documents.add(new Document("China vs. Paris Hilton", "China declared initial coin offerings illegal with immediate effect on Monday, stopping in its tracks a key fundraising vehicle for digital token sales, worth at least $1.25 billion this year. Bitcoin fell as low as $4,098, 16 percent below Friday's close, before paring losses, as investors and speculators mulled the biggest regulatory challenge so far to the burgeoning digital-currency market. Meanwhile, Paris Hilton, the celebrity famous for being famous, jumped on the ICO bandwagon by taking to Twitter to back a new currency called Lydian. The move underscores the craze for the financing mechanism that raises money directly from the public.", 
		LanguageCode.ENGLISH));
	documents.add(new Document("Aerospace giant",  "United Technologies Corp. will buy Rockwell Collins Inc. for about $23 billion in one of the biggest aviation deals ever. The resulting aircraft-parts giant will be better positioned to withstand competition from planemakers Boeing Co. and Airbus SE amid a slew of pricing discounts and output growth. The fragmented nature of the industry suggests the deal will receive the regulatory greenlight, analysts say. Rockwell shareholders will receive $140 a share in cash and stock, an 18 percent premium to the Aug. 4 close, before Bloomberg News reported on deal talks. Rockwell shares last traded at $130.61.", 
		LanguageCode.ENGLISH));
	documents.add(new Document("Markets rise", 
		"European stocks rebounded after a mixed Asia session, as data from China to the euro area signaled the global recovery remains intact. West Texas Intermediate crude rose 1 percent to $47.78 a barrel and copper extended its rally to a three-year high. Futures on the S&P 500 Index fell 0.27 percent at 5:50 a.m. Eastern Time.", 
		LanguageCode.ENGLISH));
	documents.add(new Document("Monetary messages", 
		"Investors are gearing up for a busy week filled with monetary pronouncements and economic data, with voting Federal Reserve officials Lael Brainard, Neel Kashkari, Robert Kaplan, and Bill Dudley hitting the stage on the heels of Friday's weak jobs report. U.S. durable-goods figures, trade-balance data, and the release of the Fed’s Beige Book will shed light on the economic trajectory, after a purchasing managers’ index indicated the euro area is poised for the fastest expansion in a decade. The Reserve Bank of Australia left benchmark interest rates unchanged at a record low of 1.5 percent for the thirteenth month running, as expected. Later this week, Mario Draghi may offer clues on European Central Bank plans to pare its bond-buying program after an interest-rate decision on Thursday.", 
		LanguageCode.ENGLISH));
	documents.add(new Document("England v Slovakia: 'Gareth Southgate's side pull through, but flaws persist'", 
		"England's 2-1 win over Slovakia at Wembley came exactly a year to the day since the same opponents were beaten in Sam Allardyce's one game in charge of the national team.\n" + 
		"\n" + 
		"Allardyce - soon to be gone after only 67 days as manager - spoke about a lucky coin he had been given by a fan before the game as he basked in the afterglow of his lone night of triumph.\n" + 
		"\n" + 
		"Fast forward 12 months and England, now under Gareth Southgate, need only victory against Slovenia at Wembley on 5 October to confirm a place at next summer's World Cup in Russia.\n" + 
		"\n" + 
		"On this evidence, however, they will need more than a lucky coin to inspire them to any serious impact against the world game's superpowers.\n" + 
		"\n" + 
		"So have England progressed since Allardyce's brief interlude in Trnava? And what major issues does Southgate have to resolve once qualification is assured, as now looks certain?", 
		LanguageCode.ENGLISH));
	documents.add(new Document("Diego Simeone: Atletico Madrid manager signs two-year contract extension", 
		"Atletico Madrid manager Diego Simeone has signed a two-year contract extension with the Spanish club.\n" + 
		"\n" + 
		"The 47-year-old Argentine, whose previous deal was due to expire at the end of the season, has committed to the club until June 2020.\n" + 
		"\n" + 
		"He is the longest-serving current La Liga manager, having been appointed in 2011.\n" + 
		"\n" + 
		"Also a former Atletico player, Simeone led the club to the Spanish league title in 2014.\n" + 
		"\n" + 
		"His side finished Champions League runners-up twice - in 2014 and 2016, both times losing to city rivals Real Madrid - but also won the Europa League in 2012 and the Spanish Cup in 2013.\n" + 
		"\n" + 
		"Atletico began this season with a 2-2 draw at newly promoted Girona, before thrashing Las Palmas 5-1. They next play away to Valencia on Saturday.\n" + 
		"\n" + 
		"Simeone originally agreed a deal that ran until 2020 two years ago, in March 2015, but he and the club agreed to a two-year reduction in September 2016.", 
		LanguageCode.ENGLISH));
	documents.add(new Document("Premier League 39th game plan 'not unnoticed by clubs' American owners'", 
		"The man behind the International Champions Cup, the pre-season tournament featuring Europe's biggest clubs, says his promotions company wants to host Premier League matches in the United States.\n" + 
		"\n" + 
		"Charlie Stillitano, executive chairman of Relevent Sports, says the success of exporting NFL games to England has not gone unnoticed by American owners of Premier League clubs.\n" + 
		"\n" + 
		"However, plans to play a so-called '39th game' abroad have been shelved by Premier League chiefs.\n" + 
		"\n" + 
		"Referring to the NFL, Stillitano told the BBC's World Football programme: \"It seems like half the season is played in England.\n" + 
		"\n" + 
		"\"That's what NFL owners look at. And don't forget, you have a couple of NFL owners that own teams in your Premier League.\"", 
		LanguageCode.ENGLISH));
	documents.add(new Document("Football clubs overspent by 30% on players - CIES Football Observatory study", 
		"European clubs paid on average 30% more than a player was worth during the recent transfer window, a study says.\n" + 
		"\n" + 
		"The CIES Football Observatory, using a transfer value algorithm, claims Paris St-Germain's proposed 180m euro (£165.7m) deal to sign Monaco forward Kylian Mbappe was the most overpriced.\n" + 
		"\n" + 
		"It says the fee was 87.4m euros (£80.4m) more than his estimated worth.\n" + 
		"\n" + 
		"The study also suggests Manchester City overpaid by 29m euros to sign Benjamin Mendy from Monaco for 57.5m.", 
		LanguageCode.ENGLISH));
	documents.add(new Document("World Cup 2018: Pat Nevin says Scotland v Slovakia game biggest for generation", 
		"Scotland's World Cup qualifier against Slovakia in October is the most important game in a generation, says former winger Pat Nevin.\n" + 
		"\n" + 
		"Following a 2-0 win over Malta on Monday, Gordon Strachan's side could finish second in Group F if they beat Slovakia then Slovenia, with eight runners-up reaching the play-offs.\n" + 
		"\n" + 
		"\"For a couple of generations, they've always had one game where it is 'lose that and it's all starting again'. This is it,\" Nevin told BBC Scotland. \"You've got to win both of them.\"", 
		LanguageCode.ENGLISH));
	
    }

}

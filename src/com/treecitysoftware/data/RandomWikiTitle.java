package com.treecitysoftware.data;

import java.util.*;

public class RandomWikiTitle {
	
	private String wikiTitle;
	private int numberOfWords;
	
	public RandomWikiTitle()
	{
		Random generator = new Random();
		numberOfWords = generator.nextInt(4) + 1;
		//System.out.println("get a title");
		wikiTitle = concatRandomWords(generateRandomWords());
		//System.out.println("got a new title");
	}
	
	private String randomWord()
	{
		Random random = new Random();
		char[] word = new char[random.nextInt(8)+3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
        for(int j = 0; j < word.length; j++)
        {
            word[j] = (char)('a' + random.nextInt(26));
        }
        return new String(word);
	}
	
	private String[] generateRandomWords()
	{
		//System.out.println("generating the words...");
	    String[] randomStrings = new String[numberOfWords];
	    
	    for(int i = 0; i < numberOfWords; i++)
	    {
	        randomStrings[i] = randomWord();
	        //System.out.println(randomStrings[i]);
	    }
	    //System.out.println("done");
	    return randomStrings;
	}
	
	private String concatRandomWords(String[] words)
	{
		//System.out.println("putting the words together");
		String wikiTitle = "";
		for(int i = 0; i < numberOfWords; ++i)
		{
			wikiTitle += words[i];
			if(i < numberOfWords - 1)
			wikiTitle += "_";
		}
		//System.out.println("done");
		return wikiTitle;
	}
	
	public String getTitle()
	{
		return wikiTitle;
	}
	
}

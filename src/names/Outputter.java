package names;

import static java.lang.StrictMath.abs;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//talk to TA about this class
//ask about comments

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Outputter {

  /**
   * Start of the program.
   */
  private final int dataStartYear;
  private final int dataEndYear;
  private final DataProcessor process;
  private final static String YEAR_ERROR = "YEAR NOT IN DATABASE";
  private final static String RANGE_ERROR = "INVALID RANGE";
  private final static String NAME_ERROR = "NAME NOT FOUND";
  private final static String GENDER_ERROR = "INVALID GENDER";
  private final static List<String> GENDERS = Arrays.asList("M", "F");

  public Outputter(String dataSource, String dataType) {
    process = new DataProcessor(dataSource, dataType);
    dataStartYear = process.getDataFirstYear();
    dataEndYear = process.getDataLastYear();
  }

  public String topNames(int year) {
    String maleName = process.getNameFromRank(year, "M", 1);
    if (maleName.equals(NAME_ERROR)) {
      return NAME_ERROR;
    } else if (maleName.equals(YEAR_ERROR)) {
      return YEAR_ERROR;
    }
    String femaleName = process.getNameFromRank(year, "F", 1);
    return maleName + "\n" + femaleName;
  }

  public String countNamesAndBabies(int year, String startsWith, String gender) {
    validateGender(gender);
    int countNames = process.countNamesStartingWith(year, startsWith, gender);
    if (countNames == -1) {
      return YEAR_ERROR;
    }
    int totalBabies = process.countBabiesByYear(year, startsWith, gender);
    return "Names: " + countNames + "\nBabies: " + totalBabies;
  }

  public List<Integer> getRanks(String name, String gender) {
    name = validateName(name);
    validateGender(gender);
    return process.getRanks(dataStartYear, dataEndYear, name, gender);
  }

  public String getTodayName(int year, String name, String gender) {
    name = validateName(name);
    validateGender(gender);
    int rank = process.getRank(year, gender, name);
    //if getRank couldn't find a name
    if (rank == 0) {
      return NAME_ERROR;
    }
    String todayName = process.getNameFromRank(dataEndYear, gender, rank);
    return todayName + " " + gender;
  }

  //how should this handle ties?
  public String mostPopularName(int start, int end, String gender) {
    validateGender(gender);
    validateRange(start, end);
    List<String> names = process.getNamesFromRank(start, end, gender, 1);
    return process.mostFrequentNames(names);
  }

  public List<String> mostPopularLetter(int start, int end) {
    validateRange(start, end);
    List<String> letters = process.mostPopularLetters(start, end, "F");
    if (letters.size() == 0) {
      return letters;
    }
    String letter = letters.remove(0).substring(0, 1);
    return process.getNamesStartingWith(letter, "F", start, end);
  }

  public List<Integer> getRanksFromRange(int start, int end, String name, String gender) {
    validateRange(start, end);
    validateGender(gender);
    name = validateName(name);
    return process.getRanks(start, end, name, gender);
  }

  //if name isn't in both years, returns 0
  public int rankChange(int start, int end, String name, String gender) {
    validateRange(start, end);
    validateGender(gender);
    name = validateName(name);
    int nameFirstRank = process.getRank(start, gender, name);
    int nameLastRank = process.getRank(end, gender, name);
    if (nameFirstRank == 0 || nameLastRank == 0) {
      return 0;
    } else {
      return nameFirstRank - nameLastRank;
    }
  }

  //for ties, returns all names
  public List<String> biggestRankChange(int start, int end, String gender) {
    validateRange(start, end);
    validateGender(gender);
    Map<String, Integer> namesToRankChangeMap = new TreeMap<>();
    List<String> namesFromFirstYear = process.getNamesStartingWith("", gender, start, start);
    if (namesFromFirstYear.isEmpty()) {
      return namesFromFirstYear;
    }
    for (String name : namesFromFirstYear) {
      namesToRankChangeMap.put(name, abs(rankChange(start, end, name, gender)));
    }
    List<String> names = process.maxOccurrences(namesToRankChangeMap);
    names.remove(names.size() - 1);
    return names;
  }

  public double getAverageRankRange(int start, int end, String name, String gender) {
    validateRange(start, end);
    validateGender(gender);
    name = validateName(name);
    float sum = 0;
    float count = 0;
    while (start <= end) {
      int rank = process.getRank(start, gender, name);
      if (rank != 0) {
        sum += rank;
        count++;
      }
      start++;
    }
    return Math.round(sum / count * 100.0) / 100.0;
  }

  //ask about this, duplication and rounding
  public List<String> highestAverageRank(int start, int end, String gender) {
    validateRange(start, end);
    validateGender(gender);
    Map<String, Integer> namesToAverageRankMap = new TreeMap<>();
    List<String> allNames = process.getNamesStartingWith("", gender, start, end);
    if (allNames.isEmpty()) {
      return allNames;
    }
    for (String name : allNames) {
      namesToAverageRankMap.put(name, -1 * (int) getAverageRankRange(start, end, name, gender));
    }
    List<String> names = process.maxOccurrences(namesToAverageRankMap);
    names.remove(names.size() - 1);
    return names;
  }

  public double getAverageRankRecent(int numYears, String name, String gender) {
    int start = dataEndYear - numYears + 1;
    //don't need to validate since already checked in getAverageRankRange
    return getAverageRankRange(start, dataEndYear, name, gender);
  }

  public List<String> namesOfRank(int start, int end, String gender, int targetRank) {
    validateGender(gender);
    validateRange(start, end);
    List<String> names = new ArrayList<>();
    while (start <= end) {
      names.add(process.getNameFromRank(start, gender, targetRank));
      start++;
    }
    return names;
  }

  public String mostFrequentRank(int start, int end, String gender, int targetRank) {
    //don't need to validate
    List<String> names = namesOfRank(start, end, gender, targetRank);
    return process.mostFrequentNames(names);
  }

  public List<String> mostCommonPrefix(int start, int end, String gender) {
    validateRange(start, end);
    validateGender(gender);
    Map<String, Integer> prefixCountMap = new TreeMap<>();
    List<String> allNames = process.getNamesStartingWith("", gender, start, end);
    if (allNames.isEmpty()) {
      return allNames;
    }
    for (String name : allNames) {
      prefixCountMap.put(name, process.countNamesStartingWithRange(start, end, name, gender));
    }
    List<String> names = process.maxOccurrences(prefixCountMap);
    names.remove(names.size() - 1);
    return names;
  }


  //validate data range input
  private void validateRange(int start, int end) {
    if ((start > end) || (start < dataStartYear) || (end > dataEndYear)) {
      throw new InvalidParameterException(RANGE_ERROR);
    }
  }

  private void validateGender(String gender) {
    if (!GENDERS.contains(gender)) {
      throw new InvalidParameterException(GENDER_ERROR);
    }
  }

  //fixes format of name to be same as dataset
  //assumed to be capital first letter lowercase rest of letters
  private String validateName(String name) {
    return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
  }

  public static void main(String[] args) {
   // Outputter Test = new Outputter("https://www2.cs.duke.edu/courses/fall20/compsci307d/assign/01_data/data/ssa_complete/", "URL");
    Outputter Test = new Outputter("ssa_complete", "FOLDER");
    System.out.println(Test.biggestRankChange(1900, 1950, "M"));
    System.out.println(Test.topNames(1990));
    System.out.println(Test.countNamesAndBabies(1900, "R", "M"));
    System.out.println(Test.countNamesAndBabies(1900, "Q", "F"));
    System.out.println(Test.getRanks("Alex", "M"));
    System.out.println((Test.getTodayName(2001, "Janet", "F")));
    System.out.println(Test.mostPopularName(2001, 2001, "F"));
    System.out.println(Test.mostPopularLetter(1900, 1910));
    System.out.println(Test.mostPopularLetter(1900, 1925));
  }
}
//[Emma]
//    Michael
//    Jessica
//    Names: 82
//    Babies: 10979
//    Names: 3
//    Babies: 111
//    [92, 109, 89, 108, 105, 92, 96, 104, 102, 114, 111, 109, 110, 120, 112, 117, 112, 128, 125, 122, 120, 120, 125, 135, 125, 133, 122, 127, 127, 123, 127, 129, 135, 130, 131, 131, 128, 132, 131, 137, 142, 149, 155, 161, 162, 165, 179, 169, 179, 180, 180, 182, 192, 197, 198, 208, 210, 210, 206, 205, 208, 210, 215, 222, 221, 217, 225, 228, 234, 242, 244, 253, 258, 260, 260, 263, 253, 253, 246, 233, 226, 228, 217, 214, 218, 200, 218, 205, 200, 206, 191, 187, 196, 184, 171, 175, 172, 153, 164, 157, 156, 148, 143, 123, 93, 80, 64, 60, 58, 60, 59, 63, 54, 52, 50, 47, 54, 55, 56, 58, 62, 62, 63, 62, 63, 71, 67, 71, 84, 83, 93, 95, 98, 110, 128, 133, 146, 151, 153]
//    Raven F
//    Emily 1
//    [Mabel, Mabell, Mabelle, Mable, Mabry, Macel, Macey, Macie, Macil, Mackie, Macy, Mada, Madalene, Madalin, Madaline, Madalyn, Maddie, Madelaine, Madeleine, Madelene, Madeliene, Madelin, Madeline, Madell, Madelon, Madelyn, Madge, Madgie, Madie, Madline, Madlyn, Madolin, Madolyn, Madonna, Madora, Mae, Maebell, Maebelle, Maeola, Mafalda, Magaret, Magdalen, Magdalena, Magdalene, Magdaline, Maggie, Magie, Magnolia, Mahala, Mahalia, Mahalie, Mai, Maida, Maidie, Maie, Maire, Maisie, Maizie, Majel, Majorie, Malinda, Malisa, Malissa, Malissia, Malissie, Mallie, Malta, Malvena, Malvie, Malvina, Malzie, Mamie, Mammie, Mamye, Manda, Mandie, Mandy, Manerva, Manervia, Manie, Manila, Manilla, Mannie, Manola, Manuela, Manuelita, Maple, Maragret, Maranda, Marcela, Marcelina, Marceline, Marcella, Marcelle, Marcelline, Marcia, Marcile, Mardell, Mardie, Marea, Maree, Mareta, Margaret, Margarete, Margaretha, Margarett, Margaretta, Margarette, Margarita, Margarite, Margart, Margaruite, Marge, Margeret, Margert, Margery, Marget, Margherita, Margie, Margit, Margo, Margorie, Margot, Margreat, Margret, Margrete, Margrett, Margretta, Margrette, Marguerette, Marguerita, Marguerite, Margueritte, Margurette, Margurite, Mari, Maria, Mariah, Mariam, Marian, Mariana, Marianna, Marianne, Maribel, Marie, Marien, Marietta, Mariette, Marilla, Marilyn, Marina, Marinda, Marine, Marion, Marita, Marjorie, Marjory, Mark, Marlene, Marlys, Marquerite, Marrie, Marry, Marsella, Marsha, Marta, Martha, Martie, Martin, Martina, Martine, Marvel, Marvella, Marvelle, Marvin, Mary, Marya, Maryalice, Maryan, Maryann, Maryanna, Maryanne, Marybell, Marybelle, Marybeth, Marye, Maryelizabeth, Maryella, Maryellen, Maryetta, Maryjane, Maryland, Marylee, Marylou, Marylouise, Marylyn, Maryon, Maryrose, Marzella, Masako, Massie, Mata, Matha, Mathilda, Mathilde, Matie, Matilda, Matilde, Mattie, Matty, Mattye, Maud, Maude, Maudell, Maudie, Maudine, Maudy, Maura, Maureen, Maurene, Maurice, Maurine, Maurita, Mavis, Maxie, Maxine, May, Maybel, Maybell, Maybelle, Maydell, Maydelle, Maye, Mayetta, Mayme, Maymie, Mayo, Mayola, Maysel, Mazelle, Mazie, Mazzie, Mearl, Meda, Media, Medie, Medora, Mela, Melanie, Melba, Melda, Melia, Melina, Melinda, Melissa, Melissie, Melita, Mella, Mellie, Melrose, Melva, Melvia, Melvie, Melvin, Melvina, Melvinia, Mena, Mennie, Mercedes, Mercie, Mercy, Meredith, Meriam, Merie, Merl, Merla, Merle, Merlie, Merlin, Merline, Merna, Merrie, Merrill, Merry, Mertice, Mertie, Mertis, Meryl, Meryle, Meta, Metha, Metta, Mettie, Micaela, Michael, Michelina, Mickey, Mida, Mignon, Mila, Milda, Mildred, Millicent, Millie, Milly, Milton, Mima, Mimi, Mimia, Mimie, Mimmie, Mina, Minda, Mineola, Minerva, Minervia, Minette, Minie, Minna, Minnetta, Minnette, Minnie, Minola, Minta, Mintha, Mintie, Mira, Miranda, Miriam, Mirla, Mirtie, Misao, Missie, Missouri, Mittie, Mitzi, Mitzie, Modell, Modena, Modesta, Modie, Molla, Mollie, Molly, Mollye, Mona, Monette, Monica, Monie, Monique, Monna, Monnie, Monta, Montana, Monte, Monteen, Montez, Montie, Montine, Mora, Morene, Morine, Moselle, Mossie, Mozel, Mozell, Mozella, Mozelle, Murel, Murial, Muriel, Murl, Murle, Murphy, Murray, Murrel, Murrell, Murriel, Murtie, Myra, Myrl, Myrla, Myrle, Myrna, Myrta, Myrtha, Myrtice, Myrtie, Myrtis, Myrtle]
//    [Ma, Mabel, Mabeline, Mabell, Mabelle, Mabeth, Mable, Mabry, Mac, Macaria, Macel, Macey, Macie, Macil, Mack, Mackie, Macle, Maclovia, Macola, Macon, Macy, Mada, Madalaine, Madaleine, Madalen, Madalena, Madalene, Madalin, Madaline, Madalon, Madalyn, Madalyne, Madalynn, Madalynne, Maddalena, Maddie, Madel, Madelaine, Madeleine, Madelen, Madelene, Madeliene, Madelin, Madelina, Madeline, Madell, Madella, Madelle, Madelon, Madelyn, Madelyne, Madelynn, Madelynne, Madena, Madge, Madgel, Madgelene, Madgeline, Madgie, Madgline, Madia, Madie, Madiline, Madilyn, Madine, Madis, Madlen, Madlene, Madlin, Madline, Madlyn, Madlyne, Madlynne, Madolin, Madoline, Madolyn, Madonna, Madora, Madrid, Mady, Mae, Maebell, Maebelle, Maedean, Maedell, Maedelle, Maeola, Maeoma, Maerene, Maetta, Mafalda, Mag, Magalene, Magaline, Magaret, Magda, Magdalen, Magdalena, Magdalene, Magdalina, Magdaline, Magdalyn, Magdelena, Magdelene, Magdelina, Magdeline, Magdlene, Magdline, Magel, Mageline, Maggie, Maggielean, Magie, Magline, Magnolia, Maguerite, Mahala, Mahaley, Mahalia, Mahalie, Mahdeen, Mai, Maida, Maidee, Maidell, Maidie, Maie, Maile, Maire, Maisie, Maitland, Maizie, Majel, Majesta, Major, Majorie, Makiko, Malcolm, Malda, Malena, Malene, Maleta, Malia, Malina, Malinda, Maline, Malisa, Malissa, Malissia, Malissie, Malita, Mallie, Mallissa, Malta, Malva, Malvena, Malvene, Malvenia, Malvery, Malvie, Malvin, Malvina, Malvine, Malzie, Mame, Mamie, Mammie, Mamye, Mana, Manda, Mandie, Mandy, Manerva, Manervia, Manetta, Manette, Manie, Manila, Manilla, Mannette, Mannie, Manola, Manon, Manuel, Manuela, Manuelita, Manuella, Many, Manya, Maple, Mar, Mara, Marabelle, Maragaret, Maragret, Maralee, Maralyn, Maranda, Maratha, Maravene, Marba, Marbeth, Marceda, Marcedes, Marceil, Marceille, Marcel, Marcela, Marcele, Marcelena, Marcelene, Marcelia, Marcelina, Marceline, Marcell, Marcella, Marcelle, Marcellene, Marcellia, Marcellina, Marcelline, Marcelyn, Marcena, Marcene, Marcetta, March, Marcheta, Marchetta, Marcia, Marciana, Marcie, Marciel, Marcile, Marcilene, Marcilla, Marcille, Marcine, Marcus, Marcy, Marda, Mardel, Mardell, Mardella, Mardelle, Mardie, Mardine, Marea, Maree, Marella, Maren, Marena, Marene, Mareta, Maretta, Marg, Marga, Margaet, Margarat, Margare, Margaree, Margaret, Margareta, Margarete, Margareth, Margaretha, Margarethe, Margaretmary, Margarett, Margaretta, Margarette, Margarida, Margarie, Margarine, Margarit, Margarita, Margarite, Margaritte, Margart, Margaruite, Margary, Margaurite, Marge, Margean, Margeart, Margee, Margel, Margene, Margeree, Margeret, Margerete, Margerett, Margerette, Margerie, Margerite, Margert, Margery, Marget, Margetta, Margeurite, Margey, Marggie, Margherita, Margherite, Margia, Margie, Margine, Margit, Margo, Margorie, Margory, Margot, Margrate, Margreat, Margree, Margreet, Margret, Margrete, Margrethe, Margrett, Margretta, Margrette, Margrie, Marguarite, Marguerete, Marguerette, Margueriete, Margueriette, Marguerita, Marguerite, Margueritt, Margueritta, Margueritte, Marguery, Marguret, Margurete, Margurette, Marguriete, Marguriette, Margurite, Marguritte, Margy, Mari, Maria, Mariadejesus, Mariah, Marialice, Marialyce, Mariam, Marian, Mariana, Mariane, Marianita, Mariann, Marianna, Marianne, Maribel, Maribell, Maribelle, Maribeth, Marice, Marida, Maridell, Marie, Mariea, Marieange, Mariejeanne, Mariel, Mariella, Marielle, Mariellen, Marielouise, Marien, Marienne, Marieta, Marietta, Mariette, Marifrances, Marigene, Marigold, Marigrace, Marijane, Marijean, Marijo, Mariko, Marilda, Marilea, Marilee, Marilene, Marilla, Marillyn, Marilou, Marilouise, Marilu, Marilyn, Marilyne, Marilynn, Marilynne, Marina, Marinda, Marine, Marinel, Marinell, Marinelle, Mario, Marion, Marionette, Maris, Marise, Marita, Marium, Marja, Marjarie, Marjean, Marjie, Marjolaine, Marjoree, Marjoria, Marjorie, Marjorine, Marjory, Mark, Marla, Marlea, Marleah, Marlean, Marlee, Marlena, Marlene, Marles, Marleta, Marlin, Marline, Marlis, Marlon, Marlowe, Marlyn, Marlynn, Marlys, Marna, Marne, Marnell, Marnette, Marnie, Marny, Marolyn, Marquerite, Marquita, Marrian, Marrie, Marrietta, Marrion, Marry, Marsella, Marsena, Marsha, Marshall, Marta, Marteen, Martell, Martella, Martelle, Martena, Marth, Martha, Marthajane, Marthann, Martharee, Marthe, Marthella, Marthena, Marther, Marthia, Marthie, Marthy, Martie, Martiel, Martin, Martina, Martine, Marty, Marva, Marvalene, Marveen, Marvel, Marvelene, Marveline, Marvell, Marvella, Marvelle, Marvelyn, Marvene, Marvie, Marvil, Marvin, Marvina, Marvine, Marvis, Marvyl, Marx, Mary, Marya, Maryagnes, Maryalice, Maryalyce, Maryan, Maryana, Maryann, Maryanna, Maryanne, Marybel, Marybell, Marybelle, Marybeth, Marycatherine, Maryclaire, Maryclare, Marydell, Marye, Maryelizabeth, Maryella, Maryellen, Maryetta, Maryette, Maryfrances, Marygrace, Maryhelen, Maryjane, Maryjayne, Maryjean, Maryjo, Maryl, Maryland, Marylea, Marylee, Marylena, Marylene, Marylin, Maryln, Marylois, Marylou, Marylouise, Marylu, Marylue, Marylyn, Marylynn, Marymae, Marymargaret, Marynell, Maryon, Maryrose, Maryruth, Marysue, Marzee, Marzell, Marzella, Marzelle, Marzetta, Marzette, Marzie, Masa, Masae, Masako, Masaye, Masayo, Masel, Masie, Masil, Mason, Massie, Masue, Masuko, Masumi, Mata, Matea, Matella, Matha, Mathel, Mathie, Mathilda, Mathilde, Matiana, Matie, Matilda, Matilde, Matildia, Matrona, Matsue, Matsuko, Matsuye, Matsuyo, Matthew, Mattie, Mattielee, Matty, Mattye, Matylda, Maud, Mauda, Maude, Maudean, Maudeen, Maudeline, Maudell, Maudella, Maudelle, Maudena, Maudene, Maudestine, Maudie, Maudine, Maudry, Maudy, Mauline, Maura, Maureen, Maurene, Maurice, Mauricia, Maurie, Maurietta, Maurine, Maurita, Mava, Mavel, Mavie, Mavin, Mavis, Max, Maxcine, Maxene, Maxie, Maxiene, Maxima, Maximina, Maxine, Maxola, Maxwell, Maxyne, May, Maybel, Maybell, Maybelle, Maybelline, Maybeth, Mayble, Maycel, Maycle, Mayda, Maydean, Maydel, Maydell, Maydelle, Maydene, Maye, Mayetta, Mayfred, Maylene, Mayme, Maymie, Maynard, Maynette, Mayo, Mayola, Mayoma, Mayona, Mayre, Mayree, Mayrene, Maysel, Maysie, Maytha, Maythel, Mayvis, Maywood, Mayzell, Mayzelle, Mazel, Mazell, Mazella, Mazelle, Mazie, Mazola, Mazy, Mazzie, Meadie, Meadow, Mealie, Mearl, Mearle, Mecie, Meda, Meddie, Media, Medie, Medora, Medrith, Mee, Megan, Mel, Mela, Melaine, Melania, Melanie, Melba, Melchora, Melcina, Melda, Meleta, Melia, Melicent, Melida, Melina, Melinda, Melissa, Melissia, Melissie, Melita, Mell, Mella, Melle, Meller, Mellicent, Mellie, Melma, Melna, Melody, Melonee, Melrose, Melster, Melva, Melvena, Melvene, Melvenia, Melverine, Melvia, Melvie, Melville, Melvin, Melvina, Melvine, Melvinia, Melvis, Melzie, Melzina, Memory, Memphis, Mena, Mennie, Mentie, Merced, Merceda, Mercedes, Mercedese, Mercer, Mercia, Mercides, Mercie, Mercile, Mercy, Merdell, Merdis, Merdith, Meredith, Meredyth, Merel, Merelyn, Meri, Merial, Meriam, Merian, Meribah, Meribeth, Merica, Merida, Merideth, Merie, Meriel, Meriem, Meril, Merilda, Merilyn, Merilynn, Merinda, Merion, Merita, Merl, Merla, Merle, Merlean, Merlee, Merlene, Merley, Merlie, Merlin, Merline, Merlisa, Merlyn, Merna, Mernie, Merrel, Merrell, Merri, Merrial, Merriam, Merrie, Merriel, Merril, Merrilee, Merrill, Merrilyn, Merritt, Merry, Merta, Mertha, Mertice, Mertie, Mertis, Mertle, Merva, Mervin, Mervyn, Merwyn, Mery, Meryl, Meryle, Mescal, Meta, Metha, Methel, Methyl, Metta, Mettie, Miami, Micaela, Michael, Michaela, Michaelina, Michaeline, Michalena, Michalene, Michalina, Michaline, Micheal, Michela, Michele, Michelena, Michelina, Micheline, Michelle, Michi, Michie, Michiko, Michiye, Michiyo, Mickelina, Mickey, Mickie, Micky, Mida, Middie, Midge, Midori, Midred, Mieko, Mignon, Mignonette, Mignonne, Miguela, Mike, Mila, Milada, Miladie, Milady, Milbra, Milbrey, Milda, Milderd, Mildra, Mildred, Mildren, Mildreth, Mildrid, Milena, Miley, Milford, Milla, Millard, Miller, Millicent, Millie, Millison, Milly, Milo, Milred, Milton, Mima, Mimi, Mimia, Mimie, Mimmie, Mina, Minda, Mineko, Mineola, Minerva, Minervia, Minetta, Minette, Minie, Minna, Minne, Minneola, Minnetta, Minnette, Minnie, Minnielee, Minola, Minor, Minta, Mintha, Mintie, Minyon, Miona, Mira, Mirah, Miram, Miranda, Miriam, Mirian, Mirl, Mirla, Mirle, Mirriam, Mirth, Mirtie, Mirtle, Misae, Misako, Misao, Misaye, Misayo, Mishie, Missie, Missouri, Missouria, Misue, Mitchell, Mitsu, Mitsue, Mitsuko, Mitsuru, Mitsuye, Mitsuyo, Mittie, Mitzi, Mitzie, Miyako, Miye, Miyeko, Miyo, Miyoko, Miyuki, Modean, Modell, Modelle, Modena, Modene, Modenia, Modest, Modesta, Modestine, Modie, Modine, Moina, Moira, Molene, Moline, Molla, Molley, Mollie, Molly, Mollye, Momie, Momoe, Momoyo, Mona, Monda, Monell, Moneta, Monetta, Monette, Monia, Monica, Monice, Monie, Monique, Monita, Monna, Monnie, Monta, Montana, Monte, Monteen, Montez, Montie, Montine, Montra, Montrose, Montry, Monty, Monzell, Monzelle, Moonyeen, Mora, Moree, Morella, Morene, Morgan, Morine, Morjorie, Morley, Morna, Morris, Morton, Mose, Mosella, Moselle, Moses, Mosetta, Mosie, Mossie, Motie, Moudie, Mourine, Moy, Moya, Moyra, Mozel, Mozell, Mozella, Mozelle, Mozetta, Mozter, Mrytle, Muggie, Muguette, Mural, Murdie, Murdis, Murel, Murial, Muriel, Murielle, Muril, Murl, Murle, Murlean, Murlene, Murline, Murna, Murphy, Murray, Murrel, Murrell, Murriel, Murry, Murtha, Murtie, Murtis, Musa, Musetta, Musette, Musie, Mutsuko, Myla, Myldred, Mylie, Myna, Myra, Myrabelle, Myraline, Myrdell, Myrdis, Myree, Myrel, Myrene, Myreta, Myriam, Myrie, Myril, Myrl, Myrla, Myrle, Myrlee, Myrlene, Myrna, Myrne, Myron, Myrt, Myrta, Myrtes, Myrth, Myrtha, Myrtice, Myrtie, Myrtis, Myrtise, Myrtle, Myrtlee, Myrtlene, Myrtus]
//
//    Process finished wi
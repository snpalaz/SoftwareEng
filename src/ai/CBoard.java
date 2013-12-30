package ai;

public class CBoard {
    public static int holes = 6;    /* kiþi baþý 6þar oyuk*/       
    public static int StartingStones;   //oyuk baþý 4er taþ
    public static int TotalStones; // toplam taþ sayýsý	
    
    /* THE BOARD:
    							P1[6]  P1[5]  P1[4]  P1[3]  P1[2]  P1[1]
    	                Mancala1                                        Mancala2
    							P2[1]  P2[2]  P2[3]  P2[4]  P2[5]  P2[6]
    indisleme P1[1] den baþlayýp, saat yönünün tersi yönde Mancala2 ye doðru gidiyor.*/   
    
    public int[] gameBoard = new int[(holes + 1) * 2]; //14 elemanlý gameBoard dizisi       
    public static int indexP1Home = holes; //indexP1Home=6
    public static int indexP2Home = ((holes + 1) * 2) - 1;//indexP2Home=13   
    /* Statuse göre hamleler
    	(1) sýra P1de
    	(2) sýra P2de
    	(3) P1 kazandý
    	(4) P2 kazandý
    	(5) Berabere*/
    public int Status;
    
    /*yeni oyun baþlat*/
    public CBoard() 
    {
        NewGame();
    }
    
    // -----------------------------------------------------------------------------------------
    // Method: NewGame()
    // Deðiþkenleri: -
    // Dönüþ Deðeri: -
    // Amaç: deðerler atanýr 
    //	1. oyuncu için
    // -----------------------------------------------------------------------------------------
    public void NewGame()
    {
        // belirtilen sayýlarý oyuklara ata
        for (int x = 0; x < holes; x++)
        {
            gameBoard[x] = StartingStones;//P1 oyuklarýný doldur
            gameBoard[x + (holes + 1)] = StartingStones;//P2 oyuklarýný doldur
        }

        indexP1Home = holes;
        indexP2Home = ((holes + 1) * 2) - 1;

        // kaleleri sýfýrla
        gameBoard[indexP1Home] = 0;//gameBoard[6]=0
        gameBoard[indexP2Home] = 0;//gameBoard[13]=0

        // sýrayý P1e ver
        Status = 2;
    }
    
    // -----------------------------------------------------------------------------------------
    // Method: ValidMove
    // Deðiþkenleri: her iki player içinde bin index i 1-6 arasýnda.
    // Dönüþ Deðeri: bool: uygun olup olmadýðýný döndür
    // Amaç: uygun mu deðil mi tespit et 
    // -----------------------------------------------------------------------------------------
    public boolean ValidMove(int move)
    {
    	boolean testValid = false;

        if ((move >= 1) && (move <= holes))/*1 <= move <= 6*/
        {
            if ((Status == 1) && (gameBoard[move - 1] > 0))
                testValid = true;/*Sýra P1 de ve ???*/
            if ((Status == 2) && (gameBoard[(move - 1) + (holes + 1)] > 0))
                testValid = true;/*Sýra P2 de ve ???*/
        }
        return testValid;
    }
    
 // -----------------------------------------------------------------------------------------
    // Method: MakeMove
    // Deðiþkenleri: oynanmak istenen hamle
    // Dönüþ Deðeri: -
    // Amaç: Taþlarý saatin tersi yönünde daðýt
     // -----------------------------------------------------------------------------------------
    public void MakeMove(int move)
    {
        int numStonesInBin;
        int tempCounter = 0;

        int arrayIndexOfP1Move = move - 1;
        int indexOfP1FirstDropStone = arrayIndexOfP1Move + 1;

        int arrayIndexOfP2Move = (move - 1) + (holes + 1);
        int indexOfP2FirstDropStone = arrayIndexOfP2Move + 1;

        int moduloIndex = (holes + 1) * 2;

        int indexOfP1FirstBin = 0;
        int indexOfP1LastBin = indexP1Home - 1;

        int indexOfP2FirstBin = holes + 1;
        int indexOfP2LastBin = indexP2Home - 1;

        int x;//counter
        boolean GoAgain = false;
        int sum1, sum2;
        boolean GameOver = false;

        // hamle uygun ise oyuncu bu hamleyi yapabilir
        if (ValidMove(move))
        {
            switch (Status)
            {
                case 1:
                    {
                        // Oyuncu tarafýndaki taþlar
                        numStonesInBin = gameBoard[arrayIndexOfP1Move];

                        // Boþ oyuklar kaldýrýlýr
                        gameBoard[arrayIndexOfP1Move] = 0;

                        // Taþlarý saatin tersi yönünde daðýt
                        // Rakibin kalesini atla
                        for (x = 0; x < (numStonesInBin + tempCounter); x++)
                        {
                            // karþýnýn kalesi deðilse taþ koy
                            if (!(((indexOfP1FirstDropStone + x) % moduloIndex) == indexP2Home))
                            {
                                gameBoard[(indexOfP1FirstDropStone + x) % moduloIndex]++;
                            }
                            else 
                                tempCounter++;
                        }

                        // capture u kontrol et
                        int indexOfBinOfLastStone = (indexOfP1FirstDropStone + x - 1) % (moduloIndex);

                        // bizim tarafta isek
                        if ((indexOfBinOfLastStone >= indexOfP1FirstBin) &&
                                (indexOfBinOfLastStone <= indexOfP1LastBin))
                        {
                            // goal ise
                            int currBin = indexOfBinOfLastStone + 1;//currBin 1-6 arasý bin no su.	

                            // son taþýn atýldýðý yer
                            int oppositeBin = holes + 1 - currBin;//currBin in karþýsýndaki bin no su.

                            int indexOfOppositeBin = oppositeBin + holes;

                            if ((gameBoard[indexOfBinOfLastStone] == 1) &&//son taþýn koyulduðu bin de 1 taþ olmalý
                                    (gameBoard[indexOfOppositeBin] > 0) &&//son taþýn koyulduðu binin karþýsý boþ olmamalý
                                    (indexOfBinOfLastStone != indexP1Home))//son taþýn koyulduðu home olmamalý.
                            {
                                gameBoard[indexP1Home] += gameBoard[indexOfBinOfLastStone] +
                                                          gameBoard[indexOfOppositeBin];
                                gameBoard[indexOfBinOfLastStone] = 0;
                                gameBoard[indexOfOppositeBin] = 0;
                            }
                        }

                        if (indexOfBinOfLastStone == indexP1Home)
                            GoAgain = true;
                        break;
                    }

                // P2 oyuncunun hamlesi ise, manipule et
                case 2:
                    {
                        // p2 tarafýndaki taþlarýn sayýsý
                        numStonesInBin = gameBoard[arrayIndexOfP2Move];

                        // Boþ oyuklar kaldýrýlýr
                        gameBoard[arrayIndexOfP2Move] = 0;

                        // Taþlarý saatin tersi yönünde daðýt
                        // Rakibin kalesini atla
                        for (x = 0; x < (numStonesInBin + tempCounter); x++)
                        {
                            // kale deðilse taþ koy
                            if (!(((indexOfP2FirstDropStone + x) % moduloIndex) == indexP1Home))
                            {
                                gameBoard[(indexOfP2FirstDropStone + x) % moduloIndex]++;
                            }
                            else
                                tempCounter++; 
                        }

                        //  capture u kontrol et
                        int indexOfBinOfLastStone = (indexOfP2FirstDropStone + x - 1) % moduloIndex;

                        // karþý tarafta isek
                        if ((indexOfBinOfLastStone >= indexOfP2FirstBin) && (indexOfBinOfLastStone <= indexOfP2LastBin))
                        {
                            // son taþýn býrakýldýðý oyuk
                            int currBin = indexOfBinOfLastStone - holes;

                            int oppositeBin = holes + 1 - currBin;

                            int indexOfOppositeBin = oppositeBin - 1;

                            if ((gameBoard[indexOfBinOfLastStone] == 1) && (gameBoard[indexOfOppositeBin] > 0) && (indexOfBinOfLastStone != indexP2Home))
                            {
                                gameBoard[indexP2Home] += gameBoard[indexOfBinOfLastStone] + gameBoard[indexOfOppositeBin];
                                gameBoard[indexOfBinOfLastStone] = 0;
                                gameBoard[indexOfOppositeBin] = 0;
                            }
                        }

                        if (indexOfBinOfLastStone == indexP2Home)
                            GoAgain = true;
                        break;
                    }
                default: break;
            }

            // kimin oynayacaðýný belirle
            if (!GoAgain)
            {
                // P1 oynadý P2ye geç
                if (Status == 1)
                    Status = 2;
                // P2 oynadý P1e geç
                else if (Status == 2)
                    Status = 1;
            }

            sum1 = sum2 = 0;

            for (x = 0; x < holes; x++)
            {
                sum1 += gameBoard[x];
                sum2 += gameBoard[x + (holes + 1)];
            }

            //P1 tarafýnda taþ kalmadýysa kalan taþlarý aktar oyunu bitir
            if (sum1 == 0)
            {
                // P2 tarafýnda kalan taþlarý aktar
                gameBoard[indexP2Home] += sum2;

                // oyukarý sýfýrla
                for (x = 0; x < holes; x++)
                     gameBoard[x + (holes + 1)] = 0;

                // oyun bittiðini belirt
                GameOver = true;
            }

            // P2 tarafýnda taþ kalmadýysa kalan taþlarý aktar oyunu bitir
            else if (sum2 == 0)
            {
                // P1 tarafýnda kalan taþlarý aktar
                gameBoard[indexP1Home] += sum1;

                // oyukarý sýfýrla
                for (x = 0; x < holes; x++)
                    gameBoard[x] = 0;

                // oyun bittiðini belirt
                GameOver = true;
            }

            // Kim kazandý?
            if (GameOver)
            {
                // P1's
                if (gameBoard[indexP1Home] > gameBoard[indexP2Home])
                    Status = 3;
                // P2's 
                else if (gameBoard[indexP2Home] > gameBoard[indexP1Home])
                    Status = 4;
                // Berabere
                else
                    Status = 5;
            }
        }
    }
    
    public void OutputBoard(int bin){}			// ekrana yazdýrma
    
    // -----------------------------------------------------------------------------------------
    // Method: MakeCopyIn
    // Deðiþkenleri: CBoard *destination (pointer to the copy of the current board's configuration)
    // Dönüþ Deðeri: -
    // Amaç: kullanýlan tahtanýn ayarlarýný kopyalar
    // -----------------------------------------------------------------------------------------
    public void MakeCopyIn(CBoard destination)
    {
        //destination = new CBoard();
        // dizideki deðerleri kopyala
        for (int x = 0; x < ((holes + 1) * 2); x++)
            destination.gameBoard[x] = this.gameBoard[x];
   
        // status deðerini kopyala
        destination.Status = Status;
        CBoard.indexP1Home = holes;
        CBoard.indexP2Home = ((holes + 1) * 2) - 1;
    }
    
    // -------------------------------------------------------------------
    // struct: Node
    // Amaç: aðaç yapýsýndaki bilgileri tutmak için
    // -------------------------------------------------------------------
//    class Node
//    {
//        public CBoard board;
//        public int evalFunc;
//        public int minimaxMove;
//        public int nodeDepth;
//    }; 
}

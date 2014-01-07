package ai;

import java.util.*;

//---------------------------------------------------------------
// Class: P1AI
// Amaç: Bu sýnýf bütün ihtimallerin tutulduðu ve arama yöntemlerinin
// uygulanarak doðru hareketin karar verildiði sýnýftýr
//---------------------------------------------------------------
public class P1AI {
    //deðiþkenler
    private int searchDepth;
    public boolean[] heuristics = new boolean[6];
    public P1AI() { }
    
    // -------------------------------------------------------------------
    // Method: AI
    // Deðiþkenleri: int heuristics[], kullanacaðýmýz heuristiclerin tutulduðu liste
    // Dönüþ Deðeri: Yok
    // Amaç: Heuristik yapýsýný oluþturmak
    // -------------------------------------------------------------------
    public P1AI(boolean[] h, int depth)
    {
        // P1AI için deðiþkenler
        heuristics[0] = h[0];
        heuristics[1] = h[1];
        heuristics[2] = h[2];
        heuristics[3] = h[3];
        heuristics[4] = h[4];
        heuristics[5] = h[5];
        searchDepth = depth;
    }
    
    // -------------------------------------------------------------------
    // Method: Reset
    // Deðiþkenleri: int heuristics[], kullanýlan heuristiclerin tutulduðu liste
    // Dönüþ Deðeri: Yok
    // Amaç: Heuristik yapýsýný oluþturmak 
    // -------------------------------------------------------------------
    public void reset(boolean[] h, int depth)
    {
        // P1AI için deðiþkenler
        heuristics[0] = h[0];
        heuristics[1] = h[1];
        heuristics[2] = h[2];
        heuristics[3] = h[3];
        heuristics[4] = h[4];
        heuristics[5] = h[5];
        searchDepth = depth;
    }
    
    //------------------------------------------------------------------------------------------
    // Fonksiyonu: Rakibimle aramda ne kadar fark var
    // Amaç: Kendi kalemdeki taþlar - rakibin kalesindeki taþlar.Yüksek deðerler bizim için iyi
    //	
    //------------------------------------------------------------------------------------------
    public int H1(CBoard theboard)
    {
        // Return (kendi kalemdeki taþlar - rakibin kalesindeki taþlar)
        return (theboard.gameBoard[CBoard.indexP1Home] - theboard.gameBoard[CBoard.indexP2Home]);
    }


    //------------------------------------------------------------------------------------------
    // Fonksiyonu: Kazanmaya ne kadar yakýným
    // Amaç: 1 + oyundaki toplam taþlarýn yarýsý .  Yüksek deðerler bizim için iyi
    //------------------------------------------------------------------------------------------
    public int H2(CBoard theboard)
    {
        // Return (bütün taþlarýn sayýsý - kazanmayý garantilememiz için kaç tane taþ lazým )
        int half = CBoard.TotalStones / 2;
        return (CBoard.TotalStones - (half + 1 - theboard.gameBoard[CBoard.indexP1Home]));
    }
    
    //------------------------------------------------------------------------------------------
    // Fonksiyonu: Rakibim kazanmaya ne kadar yakýn (ne kadar taþa ihtiyacý var )
    // Amaç: 1 + oyundaki toplam taþlarýn yarýsý .  Yüksek deðerler bizim için iyi
    //------------------------------------------------------------------------------------------
    public int H3(CBoard theboard)
    {
        // Return (how many opponent needs to guarantee win)
        int half = CBoard.TotalStones / 2;
        return (half + 1 - theboard.gameBoard[CBoard.indexP2Home]);
    }


    //------------------------------------------------------------------------------------------
    // Fonksiyonu: Kaleme yakýn taþlarýn sayýsý
    // Amaç: Oyuncunun kalesine 1/3 oranýnda yakýn çukurlardaki taþlarýn sayýsý toplamý
    //------------------------------------------------------------------------------------------
    public int H4(CBoard theboard)
    {
        // Bakýlacak çukur sayýsý
        int num = CBoard.holes / 3;

        // 1/3 oranýndaki yakýn çukurlardaki taþlarý topla
        int sum = 0;
        for (int i = 0; i < num; i++)
            sum += theboard.gameBoard[(CBoard.indexP1Home - 1) - i];

        // Toplamý gönder
        return sum;
    }
    
    //------------------------------------------------------------------------------------------
    // Fonksiyonu: Kaleme uzak taþ sayýsý
    // Amaç: Kalemden en uzak 1/3 oranýndaki uzak çukurlardaki taþlarýn sayýsýný topla
    //------------------------------------------------------------------------------------------
    public int H5(CBoard theboard)
    {
        //bakacaðým çukur sayýsý
        int num = CBoard.holes / 3;

        //uzak çukurlardaki taþlarýn toplanmasý
        int sum = 0;
        for (int i = 0; i < num; i++)
            sum += theboard.gameBoard[i];

        //toplamý gönder
        return sum;
    }


    //------------------------------------------------------------------------------------------
    // Fonksiyonu: Oyunun ortasýndaki taþlarýn sayýsý (ne uzak ne yakýn)
    // Amaç: Oyunda bana 1/3 oranýndaki orta uzaklýktaki çukurlardaki taþlarýn sayýlarý toplamý
    //------------------------------------------------------------------------------------------
    public int H6(CBoard theboard)
    {
        //bakýlacak çukur sayýsý
        int num = CBoard.holes / 3;

        //orta uzaklýktaki çukurdaki taþlarýn toplanmasý
        int sum = 0;
        for (int i = 0; i < num; i++)
            sum += theboard.gameBoard[num + i];

        //toplamý gönder
        return sum;
    }
    
  //------------------------------------------------------------------------------------------
    // Class: P1AI
    // Fonksiyonu: Seçili heuristiclere göre durumun deðerinin bulunmasý
    // Amaç: Bu fonksiyon ile birden fazla heuristic deðerini hesaplanabiliyor
    //------------------------------------------------------------------------------------------
    public int getTotalUtility(CBoard theboard)
    {
        //toplamý sýfýra eþitle
        int sum = 0;

        //eðer seçili ise heuristic deðerini toplam deðerine ekle
        if (heuristics[0])
            sum += H1(theboard);
        if (heuristics[1])
            sum += H2(theboard);
        if (heuristics[2])
            sum += H3(theboard);
        if (heuristics[3])
            sum += H4(theboard);
        if (heuristics[4])
            sum += H5(theboard);
        if (heuristics[5])
            sum += H6(theboard);

        //eðer bütün heuristicler seçilmemiþse ilk geçerli çukuru seçecektir çünkü 0 deðeri döner

        //toplamý gönder
        return sum;
    }


    // -------------------------------------------------------------------
    // Method: getMove
    // Deðiþkenleri: CBoard board, oyunun þu anki durumunun bir kopyasý
    // Dönüþ Deðeri: oynanacak çukur numarasý
    // Amaç: bütün olasýlýklarý dolaþ ve en iyisini seç
    // -------------------------------------------------------------------
    public int getMove(CBoard boardd)
    {
    	
        //arama aðacýnýn rootunu oluþtur
    	//CBoard.Node root = new CBoard().new Node();
    	Node root = new Node();
        //root deðerini ata
        boardd.MakeCopyIn(root.board);//root.board null geliyo
        root.evalFunc = 0;
        root.minimaxMove = 0;
        root.nodeDepth = 0;

        //minimaxDecision fonksiyonunu root için çaðýr
        return minimaxDecision(root);
    }
    
 // -------------------------------------------------------------------
    // Fonksiyonu: minimaxDecision
    // Deðiþkenleri: Node root, þu anki durumun root nodeu
    // Dönüþ Deðeri: oynanacak çukur numarasý
    // Amaç: bütün olasýlýklarý dolaþ ve en iyisini seç
    // -------------------------------------------------------------------
    public int minimaxDecision(Node currentState)
    {
        //karþýlaþtýrma bittikten sonra globalVar deðeri oynanacak doðru hareketi tutacaktýr.
        int binMove = 0;
        int value = -10000;

        //oynanabilecek en iyi deðeri ara
        Queue<Node> firstMoveQueue = new LinkedList<Node>();
        firstMoveQueue = expandState(currentState);

        //geniþletilmiþ durumlardaki deðerlere bak
        while (firstMoveQueue.size() != 0)
        {

            // yapýlacak hamle oyuncuyu tekrar hamle yapmasýný saðlýyorsa
            // tekrar oynayacakmýþ gibi hesapla
            if (currentState.board.Status == firstMoveQueue.peek().board.Status)
            {
                //eðer bulunan deðer þimdikinden büyükse deðiþtir
                int utility = maxValue(firstMoveQueue.peek(), -10000, 10000);
                if (value < utility)
                {
                    value = utility;
                    binMove = firstMoveQueue.peek().minimaxMove;
                }
            }

            //diðer oyuncunun oyununu hesapla
            else
            {
                //eðer bulunan deðer þimdikinden büyükse deðiþtir
                int utility = minValue(firstMoveQueue.peek(), -10000, 10000);
                if (value < utility)
                {
                    value = utility;
                    binMove = firstMoveQueue.peek().minimaxMove;
                }
            }

            //nodeu sýradan çýkar
            firstMoveQueue.poll();
        }

        return binMove;
    }
    
 // -------------------------------------------------------------------
    // Fonksiyonu: maxValue
    // Deðiþkenleri: Node state, oyunun þu anki durumu
    // Dönüþ Deðeri: int, en sonda bulunan hamle
    // Amaç:	þu anki durumdan oluþabilecek diðer durumlar hesaplanýyor
    // maksimum deðere sahip olan yolun parent nodu dönüyor
    // -------------------------------------------------------------------
    public int maxValue(Node state, int alpha, int beta)
    {
        //oyun bittiðinde aramadan çýk
        if (state.board.Status > 2)
            return state.evalFunc;

        //arama derinliði sýnýrýna ulaþýnca dur
        if (state.nodeDepth == searchDepth)
            return state.evalFunc;

        //deðiþken
        int value = -10000;

        //statei geniþlet ve state dizisini getir
        Queue<Node> nodesFromExpansion = new LinkedList<Node>();
        nodesFromExpansion = expandState(state);

        //geniþletilmiþ durumlardaki nodelarýn deðerine bak
        while (nodesFromExpansion.size() != 0)
        {
            // yapýlacak hamle oyuncuyu tekrar hamle yapmasýný saðlýyorsa
            // tekrar oynayacakmýþ gibi hesapla
            if (state.board.Status == nodesFromExpansion.peek().board.Status)
            {
                //eðer bulunan deðer þimdikinden büyükse deðiþtir
                int utility = maxValue(nodesFromExpansion.peek(), alpha, beta);
                if (value < utility)
                {
                    value = utility;
                }
            }

            //diðer oyuncunun oyununu hesapla
            else
            {
                //eðer bulunan deðer þimdikinden büyükse deðiþtir
                int utility = minValue(nodesFromExpansion.peek(), alpha, beta);
                if (value < utility)
                {
                    value = utility;
                }
            }

            //alpha-beta prunning
            if (value >= beta)
                return value;
            if (value > alpha)
                alpha = value;

            //nodeu sýradan çýkar
            nodesFromExpansion.poll();
        }

        //þimdiki nodeu gönder
        return value;
    }
    
 // -------------------------------------------------------------------
    // Fonksiyonu: minValue
    // Deðiþkenleri: Node state, oyunun þu andaki durumu
    // Dönüþ Deðeri: int, her durumun deðeri
    // Amaç:	þu anki durumdan oluþabilecek diðer durumlar hesaplanýyor
    // minimum deðere sahip olan yolun parent nodu dönüyor
    // -------------------------------------------------------------------
    public int minValue(Node state, int alpha, int beta)
    {
        //oyun bittiðinde aramadan çýk
        if (state.board.Status > 2)
            return state.evalFunc;

        //arama derinliði sýnýrýna ulaþýnca dur
        if (state.nodeDepth == searchDepth)
            return state.evalFunc;

        //deðiþken
        int value = 10000;

        //statei geniþlet ve state dizisini getir
        Queue<Node> nodesFromExpansion = new LinkedList<Node>();
        nodesFromExpansion = expandState(state);

        //geniþletilmiþ durumlardaki nodelarýn deðerine bak
        while (nodesFromExpansion.size() != 0)
        {

            // yapýlacak hamle oyuncuyu tekrar hamle yapmasýný saðlýyorsa
            // tekrar oynayacakmýþ gibi hesapla
            if (state.board.Status == nodesFromExpansion.peek().board.Status)
            {
                //eðer bulunan deðer þimdikinden küçükse deðiþtir
                int utility = minValue(nodesFromExpansion.peek(), alpha, beta);
                if (value > utility)
                {
                    value = utility;
                }
            }

            //diðer oyuncunun oyununu hesapla
            else
            {
                //eðer bulunan deðer þimdikinden küçükse deðiþtir
                int utility = maxValue(nodesFromExpansion.peek(), alpha, beta);
                if (value > utility)
                {
                    value = utility;
                }
            }

            //alpha-beta prunning
            if (value <= alpha)
                return value;
            if (value < beta)
                beta = value;

            //nodeu sýradan çýkar
            nodesFromExpansion.poll();
        }

        //þimdiki nodeu gönder
        return value;
    }
    
 // -------------------------------------------------------------------
    // Fonksiyonu: expandState
    // Deðiþkenleri: Node expandMe, kullanýcýnýn hareketleriyle geniþleyecek olan ilk parent node
    // Dönüþ Deðeri: std::queue<Node>, child node dizisi
    // Amaç: parent nodeun geniþletilmesiyle oluþacak child nodelar
    // -------------------------------------------------------------------
    public Queue<Node> expandState(Node expandMe)
    {
        //geniþletilmiþ nodelarýn tutulacaðý yer
        Queue<Node> nodeQueue = new LinkedList<Node>();

        int[] possibleBins = new int[CBoard.holes];	//hareket edilebilecek geçerli çukurlarýn numarasý
        int numPossibleMoves = 0;	//hareket edilebilecek çukurlarýn sayýsý

        //kimin sýrasý olduðuna karar ver
        switch (expandMe.board.Status)
        {
            // Player1in sýrasý
            case 1:
                {
                    //hareket edebileceði geçerli çukurlar
                    for (int i = 0; i < CBoard.holes; i++)
                    {
                        //eðer bir çukur içinde taþ varsa bunlarý array içinde tut ve yeni geçerli çukuru bul
                        if (expandMe.board.gameBoard[i] > 0)
                        {
                            possibleBins[numPossibleMoves] = i + 1;
                            numPossibleMoves++;
                        }
                    }

                    // expandMe den oluþabilecek bütün geçerli nodelarý bul
                    for (int j = 0; j < numPossibleMoves; j++)
                    {
                        //tempBoard oluþtur ve þu anki oyun durumunu yedekle
                        CBoard tempBoard = new CBoard();
                        expandMe.board.MakeCopyIn(tempBoard);

                        //tempNode oluþtur ve þu anki nodeu yedekle
                        //CBoard.Node tempNode = new CBoard().new Node();
                        Node tempNode = new Node();
                        
                        //hamle yap
                        tempBoard.MakeMove(possibleBins[j]);

                        //hamlenin sonucunu sakla
                        tempBoard.MakeCopyIn(tempNode.board);

                        // heuristic fonksiyonlara göre nod deðeri hesapla 
                        tempNode.evalFunc = getTotalUtility(tempBoard);

                        tempNode.nodeDepth = expandMe.nodeDepth + 1;
                        tempNode.minimaxMove = possibleBins[j];

                        //nodeu sýraya koy
                        nodeQueue.add(tempNode);
                    }
                    break;
                }

            // Player2nin sýrasý
            case 2:
                {
                    //hareket edebileceði geçerli çukurlar
                    for (int i = 0; i < CBoard.holes; i++)
                    {
                        //eðer bir çukur içinde taþ varsa bunlarý array içinde tut ve yeni geçerli çukuru bul
                        if (expandMe.board.gameBoard[i + (CBoard.holes + 1)] > 0)
                        {
                            possibleBins[numPossibleMoves] = i + 1;
                            numPossibleMoves++;
                        }
                    }

                    // expandMe den oluþabilecek bütün geçerli nodelarý bul
                    for (int j = 0; j < numPossibleMoves; j++)
                    {
                        //tempBoard oluþtur ve þu anki oyun durumunu yedekle
                        CBoard tempBoard = new CBoard();
                        expandMe.board.MakeCopyIn(tempBoard);

                        //tempNode oluþtur ve þu anki nodeu yedekle
                        //Node tempNode = new Node();
                        //CBoard.Node tempNode = new CBoard().new Node();
                        Node tempNode = new Node();
                        //hamle yap
                        tempBoard.MakeMove(possibleBins[j]);

                        //hamlenin sonucunu sakla
                        tempBoard.MakeCopyIn(tempNode.board);

                        // heuristic fonksiyonlara göre nod deðeri hesapla 
                        tempNode.evalFunc = getTotalUtility(tempBoard);

                        tempNode.nodeDepth = expandMe.nodeDepth + 1;
                        tempNode.minimaxMove = possibleBins[j];

                        //nodeu sýraya koy
                        nodeQueue.add(tempNode);
                    }
                    break;
                }

            default:
                //oyun bitti
                break;
        }

        //hareket edilecek çukurun numarasýný gönder
        return nodeQueue;
    }
}

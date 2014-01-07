package ai;

import java.util.*;

//---------------------------------------------------------------
// Class: P1AI
// Ama�: Bu s�n�f b�t�n ihtimallerin tutuldu�u ve arama y�ntemlerinin
// uygulanarak do�ru hareketin karar verildi�i s�n�ft�r
//---------------------------------------------------------------
public class P1AI {
    //de�i�kenler
    private int searchDepth;
    public boolean[] heuristics = new boolean[6];
    public P1AI() { }
    
    // -------------------------------------------------------------------
    // Method: AI
    // De�i�kenleri: int heuristics[], kullanaca��m�z heuristiclerin tutuldu�u liste
    // D�n�� De�eri: Yok
    // Ama�: Heuristik yap�s�n� olu�turmak
    // -------------------------------------------------------------------
    public P1AI(boolean[] h, int depth)
    {
        // P1AI i�in de�i�kenler
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
    // De�i�kenleri: int heuristics[], kullan�lan heuristiclerin tutuldu�u liste
    // D�n�� De�eri: Yok
    // Ama�: Heuristik yap�s�n� olu�turmak 
    // -------------------------------------------------------------------
    public void reset(boolean[] h, int depth)
    {
        // P1AI i�in de�i�kenler
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
    // Ama�: Kendi kalemdeki ta�lar - rakibin kalesindeki ta�lar.Y�ksek de�erler bizim i�in iyi
    //	
    //------------------------------------------------------------------------------------------
    public int H1(CBoard theboard)
    {
        // Return (kendi kalemdeki ta�lar - rakibin kalesindeki ta�lar)
        return (theboard.gameBoard[CBoard.indexP1Home] - theboard.gameBoard[CBoard.indexP2Home]);
    }


    //------------------------------------------------------------------------------------------
    // Fonksiyonu: Kazanmaya ne kadar yak�n�m
    // Ama�: 1 + oyundaki toplam ta�lar�n yar�s� .  Y�ksek de�erler bizim i�in iyi
    //------------------------------------------------------------------------------------------
    public int H2(CBoard theboard)
    {
        // Return (b�t�n ta�lar�n say�s� - kazanmay� garantilememiz i�in ka� tane ta� laz�m )
        int half = CBoard.TotalStones / 2;
        return (CBoard.TotalStones - (half + 1 - theboard.gameBoard[CBoard.indexP1Home]));
    }
    
    //------------------------------------------------------------------------------------------
    // Fonksiyonu: Rakibim kazanmaya ne kadar yak�n (ne kadar ta�a ihtiyac� var )
    // Ama�: 1 + oyundaki toplam ta�lar�n yar�s� .  Y�ksek de�erler bizim i�in iyi
    //------------------------------------------------------------------------------------------
    public int H3(CBoard theboard)
    {
        // Return (how many opponent needs to guarantee win)
        int half = CBoard.TotalStones / 2;
        return (half + 1 - theboard.gameBoard[CBoard.indexP2Home]);
    }


    //------------------------------------------------------------------------------------------
    // Fonksiyonu: Kaleme yak�n ta�lar�n say�s�
    // Ama�: Oyuncunun kalesine 1/3 oran�nda yak�n �ukurlardaki ta�lar�n say�s� toplam�
    //------------------------------------------------------------------------------------------
    public int H4(CBoard theboard)
    {
        // Bak�lacak �ukur say�s�
        int num = CBoard.holes / 3;

        // 1/3 oran�ndaki yak�n �ukurlardaki ta�lar� topla
        int sum = 0;
        for (int i = 0; i < num; i++)
            sum += theboard.gameBoard[(CBoard.indexP1Home - 1) - i];

        // Toplam� g�nder
        return sum;
    }
    
    //------------------------------------------------------------------------------------------
    // Fonksiyonu: Kaleme uzak ta� say�s�
    // Ama�: Kalemden en uzak 1/3 oran�ndaki uzak �ukurlardaki ta�lar�n say�s�n� topla
    //------------------------------------------------------------------------------------------
    public int H5(CBoard theboard)
    {
        //bakaca��m �ukur say�s�
        int num = CBoard.holes / 3;

        //uzak �ukurlardaki ta�lar�n toplanmas�
        int sum = 0;
        for (int i = 0; i < num; i++)
            sum += theboard.gameBoard[i];

        //toplam� g�nder
        return sum;
    }


    //------------------------------------------------------------------------------------------
    // Fonksiyonu: Oyunun ortas�ndaki ta�lar�n say�s� (ne uzak ne yak�n)
    // Ama�: Oyunda bana 1/3 oran�ndaki orta uzakl�ktaki �ukurlardaki ta�lar�n say�lar� toplam�
    //------------------------------------------------------------------------------------------
    public int H6(CBoard theboard)
    {
        //bak�lacak �ukur say�s�
        int num = CBoard.holes / 3;

        //orta uzakl�ktaki �ukurdaki ta�lar�n toplanmas�
        int sum = 0;
        for (int i = 0; i < num; i++)
            sum += theboard.gameBoard[num + i];

        //toplam� g�nder
        return sum;
    }
    
  //------------------------------------------------------------------------------------------
    // Class: P1AI
    // Fonksiyonu: Se�ili heuristiclere g�re durumun de�erinin bulunmas�
    // Ama�: Bu fonksiyon ile birden fazla heuristic de�erini hesaplanabiliyor
    //------------------------------------------------------------------------------------------
    public int getTotalUtility(CBoard theboard)
    {
        //toplam� s�f�ra e�itle
        int sum = 0;

        //e�er se�ili ise heuristic de�erini toplam de�erine ekle
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

        //e�er b�t�n heuristicler se�ilmemi�se ilk ge�erli �ukuru se�ecektir ��nk� 0 de�eri d�ner

        //toplam� g�nder
        return sum;
    }


    // -------------------------------------------------------------------
    // Method: getMove
    // De�i�kenleri: CBoard board, oyunun �u anki durumunun bir kopyas�
    // D�n�� De�eri: oynanacak �ukur numaras�
    // Ama�: b�t�n olas�l�klar� dola� ve en iyisini se�
    // -------------------------------------------------------------------
    public int getMove(CBoard boardd)
    {
    	
        //arama a�ac�n�n rootunu olu�tur
    	//CBoard.Node root = new CBoard().new Node();
    	Node root = new Node();
        //root de�erini ata
        boardd.MakeCopyIn(root.board);//root.board null geliyo
        root.evalFunc = 0;
        root.minimaxMove = 0;
        root.nodeDepth = 0;

        //minimaxDecision fonksiyonunu root i�in �a��r
        return minimaxDecision(root);
    }
    
 // -------------------------------------------------------------------
    // Fonksiyonu: minimaxDecision
    // De�i�kenleri: Node root, �u anki durumun root nodeu
    // D�n�� De�eri: oynanacak �ukur numaras�
    // Ama�: b�t�n olas�l�klar� dola� ve en iyisini se�
    // -------------------------------------------------------------------
    public int minimaxDecision(Node currentState)
    {
        //kar��la�t�rma bittikten sonra globalVar de�eri oynanacak do�ru hareketi tutacakt�r.
        int binMove = 0;
        int value = -10000;

        //oynanabilecek en iyi de�eri ara
        Queue<Node> firstMoveQueue = new LinkedList<Node>();
        firstMoveQueue = expandState(currentState);

        //geni�letilmi� durumlardaki de�erlere bak
        while (firstMoveQueue.size() != 0)
        {

            // yap�lacak hamle oyuncuyu tekrar hamle yapmas�n� sa�l�yorsa
            // tekrar oynayacakm�� gibi hesapla
            if (currentState.board.Status == firstMoveQueue.peek().board.Status)
            {
                //e�er bulunan de�er �imdikinden b�y�kse de�i�tir
                int utility = maxValue(firstMoveQueue.peek(), -10000, 10000);
                if (value < utility)
                {
                    value = utility;
                    binMove = firstMoveQueue.peek().minimaxMove;
                }
            }

            //di�er oyuncunun oyununu hesapla
            else
            {
                //e�er bulunan de�er �imdikinden b�y�kse de�i�tir
                int utility = minValue(firstMoveQueue.peek(), -10000, 10000);
                if (value < utility)
                {
                    value = utility;
                    binMove = firstMoveQueue.peek().minimaxMove;
                }
            }

            //nodeu s�radan ��kar
            firstMoveQueue.poll();
        }

        return binMove;
    }
    
 // -------------------------------------------------------------------
    // Fonksiyonu: maxValue
    // De�i�kenleri: Node state, oyunun �u anki durumu
    // D�n�� De�eri: int, en sonda bulunan hamle
    // Ama�:	�u anki durumdan olu�abilecek di�er durumlar hesaplan�yor
    // maksimum de�ere sahip olan yolun parent nodu d�n�yor
    // -------------------------------------------------------------------
    public int maxValue(Node state, int alpha, int beta)
    {
        //oyun bitti�inde aramadan ��k
        if (state.board.Status > 2)
            return state.evalFunc;

        //arama derinli�i s�n�r�na ula��nca dur
        if (state.nodeDepth == searchDepth)
            return state.evalFunc;

        //de�i�ken
        int value = -10000;

        //statei geni�let ve state dizisini getir
        Queue<Node> nodesFromExpansion = new LinkedList<Node>();
        nodesFromExpansion = expandState(state);

        //geni�letilmi� durumlardaki nodelar�n de�erine bak
        while (nodesFromExpansion.size() != 0)
        {
            // yap�lacak hamle oyuncuyu tekrar hamle yapmas�n� sa�l�yorsa
            // tekrar oynayacakm�� gibi hesapla
            if (state.board.Status == nodesFromExpansion.peek().board.Status)
            {
                //e�er bulunan de�er �imdikinden b�y�kse de�i�tir
                int utility = maxValue(nodesFromExpansion.peek(), alpha, beta);
                if (value < utility)
                {
                    value = utility;
                }
            }

            //di�er oyuncunun oyununu hesapla
            else
            {
                //e�er bulunan de�er �imdikinden b�y�kse de�i�tir
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

            //nodeu s�radan ��kar
            nodesFromExpansion.poll();
        }

        //�imdiki nodeu g�nder
        return value;
    }
    
 // -------------------------------------------------------------------
    // Fonksiyonu: minValue
    // De�i�kenleri: Node state, oyunun �u andaki durumu
    // D�n�� De�eri: int, her durumun de�eri
    // Ama�:	�u anki durumdan olu�abilecek di�er durumlar hesaplan�yor
    // minimum de�ere sahip olan yolun parent nodu d�n�yor
    // -------------------------------------------------------------------
    public int minValue(Node state, int alpha, int beta)
    {
        //oyun bitti�inde aramadan ��k
        if (state.board.Status > 2)
            return state.evalFunc;

        //arama derinli�i s�n�r�na ula��nca dur
        if (state.nodeDepth == searchDepth)
            return state.evalFunc;

        //de�i�ken
        int value = 10000;

        //statei geni�let ve state dizisini getir
        Queue<Node> nodesFromExpansion = new LinkedList<Node>();
        nodesFromExpansion = expandState(state);

        //geni�letilmi� durumlardaki nodelar�n de�erine bak
        while (nodesFromExpansion.size() != 0)
        {

            // yap�lacak hamle oyuncuyu tekrar hamle yapmas�n� sa�l�yorsa
            // tekrar oynayacakm�� gibi hesapla
            if (state.board.Status == nodesFromExpansion.peek().board.Status)
            {
                //e�er bulunan de�er �imdikinden k���kse de�i�tir
                int utility = minValue(nodesFromExpansion.peek(), alpha, beta);
                if (value > utility)
                {
                    value = utility;
                }
            }

            //di�er oyuncunun oyununu hesapla
            else
            {
                //e�er bulunan de�er �imdikinden k���kse de�i�tir
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

            //nodeu s�radan ��kar
            nodesFromExpansion.poll();
        }

        //�imdiki nodeu g�nder
        return value;
    }
    
 // -------------------------------------------------------------------
    // Fonksiyonu: expandState
    // De�i�kenleri: Node expandMe, kullan�c�n�n hareketleriyle geni�leyecek olan ilk parent node
    // D�n�� De�eri: std::queue<Node>, child node dizisi
    // Ama�: parent nodeun geni�letilmesiyle olu�acak child nodelar
    // -------------------------------------------------------------------
    public Queue<Node> expandState(Node expandMe)
    {
        //geni�letilmi� nodelar�n tutulaca�� yer
        Queue<Node> nodeQueue = new LinkedList<Node>();

        int[] possibleBins = new int[CBoard.holes];	//hareket edilebilecek ge�erli �ukurlar�n numaras�
        int numPossibleMoves = 0;	//hareket edilebilecek �ukurlar�n say�s�

        //kimin s�ras� oldu�una karar ver
        switch (expandMe.board.Status)
        {
            // Player1in s�ras�
            case 1:
                {
                    //hareket edebilece�i ge�erli �ukurlar
                    for (int i = 0; i < CBoard.holes; i++)
                    {
                        //e�er bir �ukur i�inde ta� varsa bunlar� array i�inde tut ve yeni ge�erli �ukuru bul
                        if (expandMe.board.gameBoard[i] > 0)
                        {
                            possibleBins[numPossibleMoves] = i + 1;
                            numPossibleMoves++;
                        }
                    }

                    // expandMe den olu�abilecek b�t�n ge�erli nodelar� bul
                    for (int j = 0; j < numPossibleMoves; j++)
                    {
                        //tempBoard olu�tur ve �u anki oyun durumunu yedekle
                        CBoard tempBoard = new CBoard();
                        expandMe.board.MakeCopyIn(tempBoard);

                        //tempNode olu�tur ve �u anki nodeu yedekle
                        //CBoard.Node tempNode = new CBoard().new Node();
                        Node tempNode = new Node();
                        
                        //hamle yap
                        tempBoard.MakeMove(possibleBins[j]);

                        //hamlenin sonucunu sakla
                        tempBoard.MakeCopyIn(tempNode.board);

                        // heuristic fonksiyonlara g�re nod de�eri hesapla 
                        tempNode.evalFunc = getTotalUtility(tempBoard);

                        tempNode.nodeDepth = expandMe.nodeDepth + 1;
                        tempNode.minimaxMove = possibleBins[j];

                        //nodeu s�raya koy
                        nodeQueue.add(tempNode);
                    }
                    break;
                }

            // Player2nin s�ras�
            case 2:
                {
                    //hareket edebilece�i ge�erli �ukurlar
                    for (int i = 0; i < CBoard.holes; i++)
                    {
                        //e�er bir �ukur i�inde ta� varsa bunlar� array i�inde tut ve yeni ge�erli �ukuru bul
                        if (expandMe.board.gameBoard[i + (CBoard.holes + 1)] > 0)
                        {
                            possibleBins[numPossibleMoves] = i + 1;
                            numPossibleMoves++;
                        }
                    }

                    // expandMe den olu�abilecek b�t�n ge�erli nodelar� bul
                    for (int j = 0; j < numPossibleMoves; j++)
                    {
                        //tempBoard olu�tur ve �u anki oyun durumunu yedekle
                        CBoard tempBoard = new CBoard();
                        expandMe.board.MakeCopyIn(tempBoard);

                        //tempNode olu�tur ve �u anki nodeu yedekle
                        //Node tempNode = new Node();
                        //CBoard.Node tempNode = new CBoard().new Node();
                        Node tempNode = new Node();
                        //hamle yap
                        tempBoard.MakeMove(possibleBins[j]);

                        //hamlenin sonucunu sakla
                        tempBoard.MakeCopyIn(tempNode.board);

                        // heuristic fonksiyonlara g�re nod de�eri hesapla 
                        tempNode.evalFunc = getTotalUtility(tempBoard);

                        tempNode.nodeDepth = expandMe.nodeDepth + 1;
                        tempNode.minimaxMove = possibleBins[j];

                        //nodeu s�raya koy
                        nodeQueue.add(tempNode);
                    }
                    break;
                }

            default:
                //oyun bitti
                break;
        }

        //hareket edilecek �ukurun numaras�n� g�nder
        return nodeQueue;
    }
}



import jp.ne.docomo.smt.dev.common.exception.SdkException;
import jp.ne.docomo.smt.dev.common.exception.ServerException;
import jp.ne.docomo.smt.dev.common.http.AuthApiKey;
import jp.ne.docomo.smt.dev.dialogue.Dialogue;
import jp.ne.docomo.smt.dev.dialogue.data.DialogueResultData;
import jp.ne.docomo.smt.dev.dialogue.param.DialogueRequestParam;
	 
/*コンストラクタ（自動生成のみ）はない*/
	public class sikou {
		private String msg;
		private String result;
			
			public void go() throws SdkException, ServerException{
				// APIKEY の設定
				AuthApiKey.initializeAuth("342e37762f6e54794d6357526537436964676e5a3649462f3857702f4c6e795466766f50394d4159626938");
				
				// 雑談対話パラメータクラスを生成して、質問を設定する
				DialogueRequestParam param = new DialogueRequestParam();
				
				param.setUtt(msg);
				param.setContext("");
				param.setPlace("大阪");
				param.setMode("dialog");
				param.setCharacter(0);	//デフォルトキャラ　:指定なし 関西キャラ:20 赤ちゃん:30

				// 雑談対話クラスの生成して、リクエストを実行する
				Dialogue dialogue = new Dialogue();
				DialogueResultData resultData = dialogue.request(param);

				// 返却するメッセージに関する情報の出力
				//System.out.println("システムの返答 : " + resultData.getUtt());
				//System.out.println("音声合成用読み出力 : " + resultData.getYomi());
				result=resultData.getYomi();
			}
			

		public String getmsg(){
			return result;
		}
		public void setmsg(String msg){
			this.msg=msg;
		}
	}
	
	



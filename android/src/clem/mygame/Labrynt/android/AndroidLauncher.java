package clem.mygame.Labrynt.android;

import java.io.File;
import java.io.IOException;
import static edu.cmu.pocketsphinx.SpeechRecognizerSetup.defaultSetup;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import static android.widget.Toast.makeText;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygame.labrynt.LabryntMain;
import com.mygame.speech.SpeechRecognitionResultHandler;
import com.mygame.speech.VoiceRecognitionService;
import com.mygame.statics.Constants;
import com.mygame.statics.State;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;

public class AndroidLauncher extends AndroidApplication implements
		VoiceRecognitionService {

	private static final String TAG = "MainActivity";
	private static final String KWS_SEARCH = "move";
	private static final String WEAPON_CMD = "digits";
	private static final String MOVE_CMD = "move";
	private static final String KEYPHRASE = "move to";
	private static final String KEYPHRASE2 = "aim to";

	protected SpeechRecognizer recognizer;
	private SpeechRecognitionResultHandler speechRecognitionResultHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useWakelock = true;

		LabryntMain game = new LabryntMain(this);
		speechRecognitionResultHandler = game;

		initialize(game, config);

		new AsyncTask<Void, Void, Exception>() {
			@Override
			protected Exception doInBackground(Void... params) {
				try {
					Assets assets = new Assets(AndroidLauncher.this);
					File assetDir = assets.syncAssets();
					setupRecognizer(assetDir);
				} catch (IOException e) {
					return e;
				}
				return null;
			}

			@Override
			protected void onPostExecute(Exception result) {
				if (result != null) {
					Log.d("AndroidLauncher", "Failed to init recognizer ");
				} else {
					Log.d("AndroidLauncher", "initialized successful");
					// startListening(null);//start listening
				}
			}
		}.execute();
	}

	@Override
	protected void onDestroy() {

		if (recognizer != null) {
			recognizer.stop();
			recognizer.cancel();

		}

		super.onDestroy();
	}

	private void setupRecognizer(File assetsDir) {

		File modelsDir = new File(assetsDir, "models");
		recognizer = defaultSetup()
				.setAcousticModel(new File(modelsDir, "hmm/en-us-semi"))
				.setDictionary(new File(modelsDir, "dict/cmu07a.dic"))
				.setRawLogDir(assetsDir).setKeywordThreshold(1e-10f)
				.getRecognizer();
		recognizer.addListener(new Listener());

		// Create keyword-activation search.		
		// recognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE);
		// Create grammar-based searches.
		File menuGrammar = new File(modelsDir, "grammar/commands.gram");
		recognizer.addKeywordSearch(KWS_SEARCH, menuGrammar);

		File digitsGrammar = new File(modelsDir, "grammar/digits.gram");
		recognizer.addGrammarSearch(WEAPON_CMD, digitsGrammar);
		
	}

	@Override
	public boolean isServiceAvailable() {

		return recognizer != null;
	}

	@Override
	public void startVoiceRecognition(SpeechRecognitionResultHandler callback) {
		Log.d("startVoiceRecognition", "started");
		speechRecognitionResultHandler = callback;
		// startListening(KEYPHRASE);
		startListening(KWS_SEARCH);

	}

	class Listener implements RecognitionListener {

		public void onReadyForSpeech(Bundle params) {

		}

		@Override
		public void onBeginningOfSpeech() {
			Log.d(TAG, "onReadyForSpeech");
			speechRecognitionResultHandler.onReadyForSpeech();

		}

		@Override
		public void onPartialResult(Hypothesis hypothesis) {

			String text = hypothesis.getHypstr();

			if (text.contains(KEYPHRASE)) {
				Constants.playerState = State.Idle;
				startListening(WEAPON_CMD);
			} else if (text.contains(KEYPHRASE2)) {
				Constants.playerState = State.ATTACK;
				startListening(WEAPON_CMD);

			} else
				/* Log.d("onPartialResults", text) */;
		}

		@Override
		public void onResult(Hypothesis hypothesis) {
			if (hypothesis != null) {
				String text = hypothesis.getHypstr();
				Log.d("onResults", text);

				//if (!text.contains(KEYPHRASE)||text.contains(KEYPHRASE2)) {
					if (speechRecognitionResultHandler != null) {

						speechRecognitionResultHandler.gotSpeechResults(text);

					}
				//}

			}

		}

		@Override
		public void onEndOfSpeech() {
			// startListening(KWS_SEARCH);
			if (MOVE_CMD.equals(recognizer.getSearchName())
					|| WEAPON_CMD.equals(recognizer.getSearchName())) {
				startListening(KWS_SEARCH);
				// startListening(KEYPHRASE);
			}

			// Log.d(TAG, "onEndofSpeech");
		}
	}

	private void startListening(String searchName) throws RuntimeException {
		if (isServiceAvailable()) {
			recognizer.stop();
			recognizer.startListening(searchName);
			Log.d("startVoiceRecActivity", "started");
		}

	}

	@Override
	public void cancelService() {
		if (isServiceAvailable()) {
			recognizer.cancel();
		}

	}

	@Override
	public void messagePrompt(String text) {
		makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

	}
}

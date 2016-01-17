package com.libgdxjam.darkerthanspace.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.libgdxjam.darkerthanspace.Interf.AudioId;

public class AudioManager implements AudioId
{
	private Music musicMenu;
	private Music musicSave;
	private Music musicCredits;
	private Music musicCommandRoom;
	
	//private Sound sfxWalkHeavy;
	private Sound sfxScareWind;
	private Sound sfxDead;
	private Sound sfxDeadBig;
	private Sound sfxIceBroken;
	private Sound sfxMainMenuMove;
	private Sound sfxMainMenuSelect;
	private Sound sfxMenuSelect;
	private Sound sfxRandomScare1;
	private Sound sfxRandomScare2;
	private Sound sfxRandomScare3;
	private Sound sfxRandomScare4;
	private Sound sfxRandomScare5;
	private Sound sfxIceOn;
	private Sound sfxBombDestroyed;
	private Sound sfxRoomBigEnemy;
	private Sound sfxWalkBigEnemy;
	private Sound sfxHit;
	private Sound sfxHeartBeat;
	private Sound sfxDoor;
	private Sound sfxShoot;
	private Sound sfxText;
	private Sound sfxFlashLight;
	
	private long heartBeatId;
	
	private Assets assets;
	
	public AudioManager(Assets assets)
	{
		this.assets = assets;
	}
	
	public void loadAudio()
	{
		//Music
		if (assets.manager.update())
		{
			//Music
			musicMenu = assets.manager.get("data/audio/music/music_menu.mp3", Music.class);
			musicMenu.setLooping(true);
			musicMenu.setVolume(0);
			
			musicSave = assets.manager.get("data/audio/music/music_save.mp3", Music.class);
			musicSave.setLooping(true);
			musicSave.setVolume(0.7f);
			
			musicCredits = assets.manager.get("data/audio/music/music_credits.mp3", Music.class);
			musicCredits.setLooping(true);
			musicCredits.setVolume(0);
			
			musicCommandRoom = assets.manager.get("data/audio/music/music_commandroom.mp3", Music.class);
			musicCommandRoom.setLooping(true);
			musicCommandRoom.setVolume(0);
			
			//SFX
			//sfxWalkHeavy = assets.manager.get("data/audio/sfx/walk_heavy.mp3", Sound.class);
			sfxScareWind = assets.manager.get("data/audio/sfx/scare_wind.mp3", Sound.class);
			sfxDead = assets.manager.get("data/audio/sfx/scare_wind.mp3", Sound.class);
			sfxDeadBig = assets.manager.get("data/audio/sfx/dead_big.mp3", Sound.class);
			sfxDead = assets.manager.get("data/audio/sfx/dead.mp3", Sound.class);
			sfxIceBroken = assets.manager.get("data/audio/sfx/ice_broken.mp3", Sound.class);
			sfxMainMenuMove = assets.manager.get("data/audio/sfx/mainmenu_move.mp3", Sound.class);
			sfxMainMenuSelect = assets.manager.get("data/audio/sfx/mainmenu_select.mp3", Sound.class);
			sfxMenuSelect = assets.manager.get("data/audio/sfx/menu_select.mp3", Sound.class);
			sfxRandomScare1 = assets.manager.get("data/audio/sfx/random_scare1.mp3", Sound.class);
			sfxRandomScare2 = assets.manager.get("data/audio/sfx/random_scare2.mp3", Sound.class);
			sfxRandomScare3 = assets.manager.get("data/audio/sfx/random_scare3.mp3", Sound.class);
			sfxRandomScare4 = assets.manager.get("data/audio/sfx/random_scare4.mp3", Sound.class);
			sfxRandomScare5 = assets.manager.get("data/audio/sfx/random_scare5.mp3", Sound.class);
			sfxIceOn = assets.manager.get("data/audio/sfx/ice_on.mp3", Sound.class);
			sfxBombDestroyed = assets.manager.get("data/audio/sfx/bomb_destroyed.mp3", Sound.class);
			sfxRoomBigEnemy = assets.manager.get("data/audio/sfx/room_bigenemy.mp3", Sound.class);
			sfxWalkBigEnemy = assets.manager.get("data/audio/sfx/walk_bigenemy.mp3", Sound.class);
			sfxHit = assets.manager.get("data/audio/sfx/hit.mp3", Sound.class);
			sfxHeartBeat = assets.manager.get("data/audio/sfx/heart_beat.mp3", Sound.class);
			sfxDoor = assets.manager.get("data/audio/sfx/door.mp3", Sound.class);
			sfxShoot = assets.manager.get("data/audio/sfx/shoot.mp3", Sound.class);
			sfxText = assets.manager.get("data/audio/sfx/text.mp3", Sound.class);
			sfxFlashLight = assets.manager.get("data/audio/sfx/light_on.mp3", Sound.class);
			
			//Long ids
			heartBeatId = -1;
		}
	}
	
	public void playMusic(int musicId)
	{
		switch (musicId)
		{
		case musicMENU: stopMusic(); musicMenu.play(); break;
		
		case musicSAVE: stopMusic(); musicSave.play(); break;
			
		case musicCREDITS: stopMusic(); musicCredits.play(); break;
		
		case musicCOMMANDROOM: stopMusic(); musicCommandRoom.play(); break;

		default: break;
		}
	}
	
	public void playSound(int soundId)
	{
		switch (soundId)
		{
		//case sfxWALKHEAVY: sfxWalkHeavy.play(); break;
		case sfxSCAREWIND: sfxScareWind.play(); break;
		case sfxDEAD: sfxDead.play(); break;
		case sfxDEADBIG: sfxDeadBig.play(); break;
		case sfxICEBROKEN: sfxIceBroken.play(); break;
		case sfxMAINMENUMOVE: sfxMainMenuMove.play(); break;
		case sfxMAINMENUSELECT: sfxMainMenuSelect.play(); break;
		case sfxMENUSELECT: sfxMenuSelect.play(); break;
		case sfxRANDOMSCARE1: sfxRandomScare1.play(); break;
		case sfxRANDOMSCARE2: sfxRandomScare2.play(); break;
		case sfxRANDOMSCARE3: sfxRandomScare3.play(); break;
		case sfxRANDOMSCARE4: sfxRandomScare4.play(); break;
		case sfxRANDOMSCARE5: sfxRandomScare5.play(); break;
		case sfxICEON: sfxIceOn.play(); break;
		case sfxBOMBDESTROYED: sfxBombDestroyed.play(); break;
		case sfxROOMBIGENEMY: sfxRoomBigEnemy.play(); break;
		case sfxWALKBIGENEMY: sfxWalkBigEnemy.play(); break;
		case sfxHIT: sfxHit.play(); break;
		case sfxHEARTBEAT: if (heartBeatId == -1)heartBeatId = sfxHeartBeat.loop(); break;
		case sfxDOOR: sfxDoor.play(); break;
		case sfxSHOOT: sfxShoot.play(); break;
		case sfxTEXT: sfxText.play(); break;
		case sfxFLASHLIGHT: sfxFlashLight.play(); break;
		
		default: break;
		}
	}

	public void stopMusic()
	{
		if (musicMenu.isPlaying())
			musicMenu.stop();
		if (musicSave.isPlaying())
			musicSave.stop();
		if (musicCredits.isPlaying())
			musicCredits.stop();
		if (musicCommandRoom.isPlaying())
			musicCommandRoom.stop();
	}

	public void setMusicVolume(float musicVolume)
	{
		if (musicVolume > 0.7f)
			musicVolume = 0.7f;
		else if (musicVolume < 0)
			musicVolume = 0;
		
		if (musicMenu.isPlaying())
			musicMenu.setVolume(musicVolume);
		/*else if (musicSave.isPlaying())
			musicSave.setVolume(musicVolume);*/
		else if (musicCredits.isPlaying())
			musicCredits.setVolume(musicVolume);
		else if (musicCommandRoom.isPlaying())
			musicCommandRoom.setVolume(musicVolume);
	}
	
	public void stopLoopingSound(int soundId)
	{
		switch (soundId)
		{
		case sfxHEARTBEAT:
			{
				sfxHeartBeat.stop(heartBeatId);
				heartBeatId = -1;
			}
		break;

		default:
		break;
		}
	}
}

package fr.iutinfo.utils;

public class NoteOffice extends Effet {
	private int note ;
	
	public NoteOffice(byte dD, byte dF,int n) {
		super(dD, dF,(byte)1);
		this.note = n;
		
	}

	@Override
	public void modifNote(Note n) {
		n.setNote(this.note);
	}

}
/**
 * An enumerated value that represents the various states that a square on the board can be in.
 *
 * @author Ms Caitlin Woods
 * @version April 2022
 * 
 * Student Names and Numbers: { ENTER YOUR STUDENT NAME AND NUMBER HERE }
 */

public enum Piece {
    LOSTITEM, // means that this piece contains part of an item that has been hidden by the AI Player but has not been found 
    FOUNDITEM, // means that this piece contains part of an item that has been hidden by the AI Player and has been found.
    VACANT, // means that this piece does not contain part of any item and has not been searched by the player.
    SEARCHED // means that this piece does ot contain part of any item and has been searched by the player.
};
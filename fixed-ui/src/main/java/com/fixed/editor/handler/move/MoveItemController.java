package com.fixed.editor.handler.move;

import com.fixed.logging.Logger;
import com.fixed.editor.tab.xml.XmlTreeCell;
import javafx.scene.control.TreeItem;
import org.dom4j.Element;

/**
 * The class is responsible for moving the treeItem with following direction
 * <p>
 * <ul>
 * <li>BOTTOM</li>
 * <li>TOP</li>
 * <li>INSIDE</li>
 * </ul>
 *
 * @author Onur Karaduman
 * @since 05.11.17
 */
public class MoveItemController {

    public static Logger LOGGER = Logger.getLogger(MoveItemController.class);

    private MoveItem moveItem;

    public MoveItemController() {
        this.moveItem = new MoveItem();
    }

    public void move() {
        moveItem.validateBeforeMove();
        if (moveItem.isMovable()) {
            switch (moveItem.direction) {
                case TOP:
                    moveTop();
                    break;
                case BOTTOM:
                    moveBottom();
                    break;
                case INSIDE:
                    moveInside();
                    break;
                default:
                    moveInside();
                    break;
            }
        } else {
            LOGGER.info("The selected place is not valid to move");
        }
    }

    public void setMovedCell(XmlTreeCell movedCell) {
        this.moveItem.movedCell = movedCell;
    }

    public void setDirection(MoveItem.MoveDirection direction) {
        moveItem.direction = direction;
    }

    public void setCurrentCell(XmlTreeCell currentCell) {
        moveItem.currentCell = currentCell;
    }

    private void moveInside() {
        removeFromParent(moveItem.movedCell);
        putToInsideOf(moveItem.movedCell, moveItem.currentCell);
    }

    private void moveTop() {
        removeFromParent(moveItem.movedCell);
        putToTopOf(moveItem.movedCell, moveItem.currentCell);
    }

    private void moveBottom() {
        removeFromParent(moveItem.movedCell);
        putToBottomOf(moveItem.movedCell, moveItem.currentCell);
    }

    private void removeFromParent(XmlTreeCell removedCell) {
        TreeItem<Object> treeItem = removedCell.getTreeItem();
        treeItem.getParent().getChildren().remove(treeItem);

        Element elementItem = (Element) removedCell.getItem();
        elementItem.getParent().content().remove(elementItem);
    }

    private void putToParent(TreeItem movedItem, TreeItem currentItem) {
        currentItem.getChildren().add(movedItem);
    }

    private void putToBottomOf(XmlTreeCell movedCell, XmlTreeCell currentCell) {
        // Move Tree Item
        TreeItem<Object> movedTreeItem = movedCell.getTreeItem();
        TreeItem<Object> currentTreeItem = currentCell.getTreeItem();
        int i = currentTreeItem.getParent().getChildren().indexOf(currentTreeItem);
        putToParentCurtainPos(movedTreeItem, currentTreeItem, i + 1);

        // Move Element
        Element movedElement = (Element) movedCell.getItem();
        Element currentElement = (Element) currentCell.getItem();
        int elementIndex = currentElement.getParent().content().indexOf(currentElement);
        putToParentCurtainPos(movedElement, currentElement, elementIndex + 1);
    }

    private void putToTopOf(XmlTreeCell movedCell, XmlTreeCell currentCell) {
        // Move TreeItem
        TreeItem<Object> movedTreeItem = movedCell.getTreeItem();
        TreeItem<Object> currentTreeItem = currentCell.getTreeItem();
        int itemIndex = currentTreeItem.getParent().getChildren().indexOf(currentTreeItem);
        putToParentCurtainPos(movedTreeItem, currentTreeItem, itemIndex);

        // Move Element
        Element movedElementItem = (Element) movedCell.getItem();
        Element currentElementItem = (Element) currentCell.getItem();
        int elementIndex = currentElementItem.getParent().content().indexOf(currentElementItem);
        putToParentCurtainPos(movedElementItem, currentElementItem, elementIndex);
    }

    private void putToInsideOf(XmlTreeCell movedCell, XmlTreeCell currentCell) {
        // Move TreeItem
        TreeItem<Object> movedTreeItem = movedCell.getTreeItem();
        TreeItem<Object> currentTreeItem = currentCell.getTreeItem();
        currentTreeItem.getChildren().add(0, movedTreeItem);

        // Move Element
        Element movedElementItem = (Element) movedCell.getItem();
        Element currentElementItem = (Element) currentCell.getItem();
        currentElementItem.content().add(0, movedElementItem);
    }

    private void putToParentCurtainPos(TreeItem movedItem, TreeItem currentItem, int pos) {
        currentItem.getParent().getChildren().add(pos, movedItem);
    }

    private void putToParentCurtainPos(Element movedElement, Element currentElement, int pos) {
        currentElement.getParent().content().add(pos, movedElement);
    }


    static class MoveItem {

        /**
         * The Item which is needed to be moved
         */
        public XmlTreeCell movedCell;

        /**
         * The reference Item for moving
         */
        public XmlTreeCell currentCell;

        /**
         * The move direction
         */
        public MoveDirection direction = MoveDirection.INSIDE;

        public void validateBeforeMove() {
            if (movedCell == null) {
                throw new NullPointerException("movedCell couldn't be null");
            } else if (currentCell == null) {
                throw new NullPointerException("currentCell couldn't be null");
            }
        }

        public boolean isMovable() {
            return currentCell != movedCell;
        }

        enum MoveDirection {
            TOP, BOTTOM, INSIDE
        }
    }



   /*     private TreeItem search(Element element, FixTreeCell current) {
            TreeItem root = findRoot(current);
            return recursiveSearch(element, root);
        }*/

        /*private TreeItem recursiveSearch(Element element, TreeItem current) {
            if (((Element) current.getValue()).getQName().hashCode() == (element.getQName().hashCode())) {
                return current;
            }
            for (Object o : current.getChildren()) {
                if (((TreeItem) o).getValue() == element) {
                    return (TreeItem) o;
                } else {
                    return recursiveSearch(element, (TreeItem) o);
                }
            }
            return null;
        }*/

       /* private TreeItem findRoot(FixTreeCell current) {
            TreeItem tmpCell = current.getTreeItem();
            while (tmpCell != null) {
                if (tmpCell.getParent() == null) {
                    break;
                }
                tmpCell = tmpCell.getParent();
            }
            return tmpCell;
        }*/
}

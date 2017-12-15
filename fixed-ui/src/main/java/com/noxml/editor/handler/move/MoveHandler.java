package com.noxml.editor.handler.move;

import com.noxml.logging.Logger;
import com.noxml.editor.tab.xml.XmlTreeCell;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import org.dom4j.Element;


/**
 * @author Onur Karaduman
 * @since 03.11.17
 */
public class MoveHandler {
    public static Logger LOG = Logger.getLogger(MoveHandler.class);

    public static final DataFormat objectDataFormat = new DataFormat("application/x-java-serialized-object");

    public static MoveItemController movedItemController = new MoveItemController();

    public static class DetectListener implements EventHandler<MouseEvent> {

        private XmlTreeCell treeCell;


        public DetectListener(XmlTreeCell treeCell) {
            this.treeCell = treeCell;
        }

        @Override
        public void handle(MouseEvent event) {
            if (treeCell.getItem() == null) {
                LOG.error("There is no item in selected row");
                return;
            }
            LOG.info("Drag detected on item:" + ((Element) treeCell.getItem()).getQName().getName());
            Dragboard db = treeCell.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.put(objectDataFormat, treeCell.getItem());
            db.setContent(content);
            movedItemController.setMovedCell(treeCell);
            event.consume();
        }
    }

    public static class DragOverListener implements EventHandler<DragEvent> {
        XmlTreeCell treeCell;

        public DragOverListener(XmlTreeCell treeCell) {
            this.treeCell = treeCell;
        }

        @Override
        public void handle(DragEvent event) {

            if (treeCell.getItem() == null) {
                return;
            }

            // get scene coordinates
            Point2D sceneCoordinates = treeCell.localToScene(0d, 0d);
            double height = treeCell.getHeight();
            // get the y coordinate within the control
            double y = event.getSceneY() - (sceneCoordinates.getY());

            // if the drop is three quarters of the way down the control
            // then the drop will be a sibling and not into the tree item
            // set the dnd effect for the required action
            if (y > (height * .75d) || y < 3) {
                LOG.info("Drag get over the item: " + ((Element) treeCell.getItem()).getQName().getName());
                treeCell.setEffect(null);
                treeCell.setStyle(null);
            }
            // for moving to top of element
            else if (y < 8) {
                LOG.info("Drag top of the item: " + ((Element) treeCell.getItem()).getQName().getName());
                movedItemController.setDirection(MoveItemController.MoveItem.MoveDirection.TOP);
                treeCell.setEffect(null);

                // configure the style for border
                treeCell.setStyle("-fx-border-width: 2 0 0 0; -fx-border-color: red green yellow red;");
            } else {
                LOG.info("Drag on the item: " + ((Element) treeCell.getItem()).getQName().getName());
                movedItemController.setDirection(MoveItemController.MoveItem.MoveDirection.INSIDE);
                treeCell.setStyle(null);

                treeCell.setEffect(createInnerShadow());
            }
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        }

        private InnerShadow createInnerShadow() {
            InnerShadow shadow;
            shadow = new InnerShadow();
            shadow.setOffsetX(1.0);
            shadow.setColor(Color.web("#666666"));
            shadow.setOffsetY(1.0);
            return shadow;
        }
    }

    public static class DroppedListener implements EventHandler<DragEvent> {
        private XmlTreeCell treeCell;

        public DroppedListener(XmlTreeCell treeCell) {
            this.treeCell = treeCell;
        }

        @Override
        public void handle(DragEvent event) {
            System.out.println("dropped handled");
            movedItemController.setCurrentCell(treeCell);
            movedItemController.move();
            treeCell.setStyle(null);
            event.consume();
        }
    }
}

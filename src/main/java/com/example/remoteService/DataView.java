package com.example.remoteService;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Push()
@Route(value = "data")
public class DataView extends VerticalLayout implements HasUrlParameter<String> {
    private Integer id;
    private Notification notification;
    private final CustomService customService;
    private UpdateThread thread;
    private final Chart chart;
    private DataSeries items;
    Map<Integer, String> map = new HashMap<>(){{
        put(1,"Васеева Диана");
        put(2,"Вязыницын Федор");
        put(3,"Грицацуев Илья");
        put(4,"Децина Алексей");
        put(5,"Золотова Кира");
        put(6,"Ильяшенко Владимир");
        put(7,"Исаева Дарья");
        put(8,"Манаева Арина");
        put(9,"Манилов Павел");
        put(10,"Семёнова Алёна");
        put(11,"Серебренников Максим");
        put(12,"Сильченко Владимир");
        put(13,"Сурков Никита");
        put(14,"Фомин Алексей");
        put(15,"Чупров Александр");
    }};

    public DataView() {
        chart = new Chart();
        notification = new Notification();
        customService = CustomService.getInstance();
        configuration();
        add(chart, notification);
    }

    private void configuration(){
        Integer maxValues = customService.getMaxValues();
        items = new DataSeries("values");
        for(int x = 1;x<=maxValues;x++){
            items.add(new DataSeriesItem(x,1.0));
        }
        Configuration configuration = chart.getConfiguration();
        configuration.getChart().setType(ChartType.SPLINE);
        XAxis xAxis = configuration.getxAxis();
        xAxis.setTitle("index");
        YAxis yAxis = configuration.getyAxis();
        yAxis.setTitle("Value");
        configuration.addSeries(items);
    }

    private void emptyChart(){
        Integer maxValues = customService.getMaxValues();
        items.clear();
        for(int x = 1;x<=maxValues;x++){
            items.add(new DataSeriesItem(x,0.0));
        }
    }

    public void updateData(ConcurrentLinkedQueue<Double> values) {
        if(values == null){
            emptyChart();
            return;
        }
        Iterator<Double> iterator = values.iterator();
        Double next;
        Integer x = 1;
        items.clear();
        while (iterator.hasNext()){
            next = iterator.next();
            items.add(new DataSeriesItem(x,next));
            x++;
        }
        items.setName(map.getOrDefault(this.id, "CHECK ID WHICH SEND"));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        thread = UpdateThread.getInstance(attachEvent.getUI(), this, customService,this.id);
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        thread.flagStoped= false;
        thread = null;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String parameter) {
        if (parameter.isEmpty()) {
            notification = Notification.show("Welcome anonymous. Please add key as query parameter",
                    5000, Notification.Position.TOP_CENTER);
        } else {
            try {
                int localId = Integer.parseInt(parameter);
                if(customService.getKeys().contains(localId)){
                    this.id = localId;
                }else{
                    notification = Notification.show("Welcome anonymous. Please insert id in [1,15]",
                            5000, Notification.Position.TOP_CENTER);
                    emptyChart();
                }
            }catch (NumberFormatException ex){
                notification = Notification.show("Welcome anonymous. Please insert valid id value",
                            5000, Notification.Position.TOP_CENTER);
            }
        }
    }


    private static class UpdateThread extends Thread {
        private static UpdateThread instance;
        public static UpdateThread getInstance(UI ui, DataView view, CustomService controllerService, Integer key){
            if (instance == null){
                instance = new UpdateThread(ui, view, controllerService, key);
                instance.start();
            }else{
                instance.view = view;
                instance.ui = ui;
            }
            return instance;
        }
        private UI ui;
        private DataView view;
        private final CustomService customService;
        private final Integer key;

        public boolean flagStoped;

        private UpdateThread(UI ui, DataView view, CustomService controllerService, Integer key) {
            this.ui = ui;
            this.view = view;
            this.customService = controllerService;
            this.flagStoped = true;
            this.key = key;
        }

        @Override
        public void run() {
            try {
                while (this.flagStoped) {
                    ui.access(() -> view.updateData(customService.getValues(key)));
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

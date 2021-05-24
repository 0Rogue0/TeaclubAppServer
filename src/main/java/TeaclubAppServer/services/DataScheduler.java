package TeaclubAppServer.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
@ManagedResource(
        objectName = "Practice24MBeans:category=MBeans,name=DataScheduler"
)
public class DataScheduler {
    private final ShopCartService shopCartService;
    private final ItemService itemService;
    @Value("${dir.result}")
    private String path;

    @Scheduled(cron = "0 0/30 * * * *")
    @ManagedOperation(
            description = "Deletes all contents of the directory and " +
                    "writes all data from the database to it every 30 minutes"
    )
    public void start() throws IOException, NullPointerException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        log.info("SCHEDULED task started at {}", formatter.format(date));

        File dir = ResourceUtils.getFile(path);
        String absPath = dir.getAbsolutePath();
        Arrays.stream(Objects.requireNonNull(dir.listFiles())).forEach(file -> {
            if (file.isFile()) {
                log.info("File " + file.getName() + " was deleted: " + file.delete());
            }
        });

        BufferedWriter gBufWriter = createWriter(absPath, "shopCarts.txt");
        gBufWriter.write("id|shopCartOwner|numOfItemsInIt\n");
        shopCartService.getAllShopCarts()
                .forEach(cart -> {
                            try {
                                gBufWriter.write(
                                        String.format(
                                                "%d|%s|%d\n",
                                                cart.getId(),
                                                cart.getUser().getUserName(),
                                                cart.getItems().size()
                                        )
                                );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                );
        gBufWriter.close();

        BufferedWriter sBufWriter = createWriter(absPath, "items.txt");
        sBufWriter.write("id|nameOfItem|priceOfItem|numOfItem|shopCartOfItem\n");
        itemService.getAllItems()
                .forEach(item -> {
                            try {
                                sBufWriter.write(
                                        String.format(
                                                "%d|%s|%d|%d|%d\n",
                                                item.getId(),
                                                item.getNameOfItem(),
                                                item.getPriceOfItem(),
                                                item.getNumOfItem(),
                                                item.getShopCart().getId()
                                                //.getShopCart_id() //.getShopCart().getId()
                                        )
                                );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                );
        sBufWriter.close();

        log.info("SCHEDULED task finished.");
    }

    private BufferedWriter createWriter(String dir, String filename) throws IOException {
        return new BufferedWriter(new FileWriter(String.format("%s/%s", dir, filename)));
    }
}
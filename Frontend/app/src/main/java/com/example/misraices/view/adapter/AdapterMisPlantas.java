package com.example.misraices.view.adapter;

import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.MediaController;

import androidx.annotation.NonNull;
import androidx.emoji.text.EmojiCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.misraices.data.SQLite.Model.Planta;
import com.example.misraices.databinding.ItemMisPlantasBinding;

import java.util.List;

public class AdapterMisPlantas extends RecyclerView.Adapter<AdapterMisPlantas.ViewHolder> {
    private List<Planta> plantas;

    public AdapterMisPlantas(List<Planta> plantas) {
        this.plantas= plantas;
    }

    @NonNull
    @Override
    public AdapterMisPlantas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMisPlantasBinding binding = ItemMisPlantasBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMisPlantas.ViewHolder holder, int position) {
        Planta planta = plantas.get(position);
        holder.binding.txtNombre.setText(planta.getNombre());
        String descripcionHtml =planta.getCuidados();
        descripcionHtml = descripcionHtml.replace("\n", "<br>");

        Spanned descripcionConFormato;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            descripcionConFormato = Html.fromHtml(descripcionHtml, Html.FROM_HTML_MODE_LEGACY);
        } else {
            descripcionConFormato = Html.fromHtml(descripcionHtml);
        }
        holder.binding.txtCuidadosPlantas.setText(EmojiCompat.get().process(descripcionConFormato));
        Glide.with(holder.binding.imgProductoDetalle.getContext())
                .load(planta.getImg())
                .centerCrop()
                .into(holder.binding.imgProductoDetalle);
        holder.binding.videoView.setVideoURI(Uri.parse(planta.getVideo()));
        MediaController mediaController = new MediaController(holder.binding.videoView.getContext());
        mediaController.setAnchorView(holder.binding.videoView);
        holder.binding.videoView.setMediaController(mediaController);
        holder.binding.videoView.seekTo(1);

    }

    @Override
    public int getItemCount() {
        return plantas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemMisPlantasBinding binding;

        public ViewHolder(ItemMisPlantasBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

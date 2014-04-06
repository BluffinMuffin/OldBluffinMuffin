namespace BluffinPokerClient
{
    partial class LobbyTrainingForm
    {
        /// <summary>
        /// Variable nécessaire au concepteur.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Nettoyage des ressources utilisées.
        /// </summary>
        /// <param name="disposing">true si les ressources managées doivent être supprimées ; sinon, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Code généré par le Concepteur Windows Form

        /// <summary>
        /// Méthode requise pour la prise en charge du concepteur - ne modifiez pas
        /// le contenu de cette méthode avec l'éditeur de code.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(LobbyTrainingForm));
            this.lblTitle = new System.Windows.Forms.Label();
            this.tableList = new Com.Ericmas001.Game.BluffinMuffin.GUI.Lobby.PokerTableList();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.pictureBox2 = new System.Windows.Forms.PictureBox();
            this.btnLeaveTable = new VIBlend.WinForms.Controls.vButton();
            this.btnJoinTable = new VIBlend.WinForms.Controls.vButton();
            this.btnAddTable = new VIBlend.WinForms.Controls.vButton();
            this.btnRefresh = new VIBlend.WinForms.Controls.vButton();
            this.btnLogOut = new VIBlend.WinForms.Controls.vButton();
            this.lblPlayerName = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).BeginInit();
            this.SuspendLayout();
            // 
            // lblTitle
            // 
            this.lblTitle.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.lblTitle.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblTitle.Location = new System.Drawing.Point(53, 3);
            this.lblTitle.Name = "lblTitle";
            this.lblTitle.Size = new System.Drawing.Size(371, 35);
            this.lblTitle.TabIndex = 5;
            this.lblTitle.Text = "Bluffin Muffin Poker Client - Training";
            this.lblTitle.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // tableList
            // 
            this.tableList.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.tableList.Location = new System.Drawing.Point(0, 149);
            this.tableList.Name = "tableList";
            this.tableList.ShowCareer = false;
            this.tableList.ShowTraining = true;
            this.tableList.Size = new System.Drawing.Size(477, 142);
            this.tableList.TabIndex = 7;
            this.tableList.OnSelectionChanged += new System.EventHandler(this.tableList_OnSelectionChanged);
            this.tableList.OnChoiceMade += new System.EventHandler(this.tableList_OnChoiceMade);
            // 
            // pictureBox1
            // 
            this.pictureBox1.Image = global::BluffinPokerClient.Properties.Resources.learn;
            this.pictureBox1.Location = new System.Drawing.Point(12, 3);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(35, 35);
            this.pictureBox1.TabIndex = 15;
            this.pictureBox1.TabStop = false;
            // 
            // pictureBox2
            // 
            this.pictureBox2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.pictureBox2.Image = global::BluffinPokerClient.Properties.Resources.learn;
            this.pictureBox2.Location = new System.Drawing.Point(430, 3);
            this.pictureBox2.Name = "pictureBox2";
            this.pictureBox2.Size = new System.Drawing.Size(35, 35);
            this.pictureBox2.TabIndex = 16;
            this.pictureBox2.TabStop = false;
            // 
            // btnLeaveTable
            // 
            this.btnLeaveTable.AllowAnimations = true;
            this.btnLeaveTable.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnLeaveTable.BackColor = System.Drawing.Color.Transparent;
            this.btnLeaveTable.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnLeaveTable.Image = global::BluffinPokerClient.Properties.Resources.leave;
            this.btnLeaveTable.Location = new System.Drawing.Point(357, 108);
            this.btnLeaveTable.Name = "btnLeaveTable";
            this.btnLeaveTable.RoundedCornersMask = ((byte)(15));
            this.btnLeaveTable.Size = new System.Drawing.Size(108, 35);
            this.btnLeaveTable.TabIndex = 21;
            this.btnLeaveTable.Text = "Leave Table";
            this.btnLeaveTable.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.btnLeaveTable.UseVisualStyleBackColor = false;
            this.btnLeaveTable.VIBlendTheme = VIBlend.Utilities.VIBLEND_THEME.METROORANGE;
            this.btnLeaveTable.Click += new System.EventHandler(this.btnLeaveTable_Click);
            // 
            // btnJoinTable
            // 
            this.btnJoinTable.AllowAnimations = true;
            this.btnJoinTable.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnJoinTable.BackColor = System.Drawing.Color.Transparent;
            this.btnJoinTable.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnJoinTable.Image = global::BluffinPokerClient.Properties.Resources.cards_mini;
            this.btnJoinTable.Location = new System.Drawing.Point(251, 108);
            this.btnJoinTable.Name = "btnJoinTable";
            this.btnJoinTable.RoundedCornersMask = ((byte)(15));
            this.btnJoinTable.Size = new System.Drawing.Size(100, 35);
            this.btnJoinTable.TabIndex = 20;
            this.btnJoinTable.Text = "Join Table";
            this.btnJoinTable.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.btnJoinTable.UseVisualStyleBackColor = false;
            this.btnJoinTable.VIBlendTheme = VIBlend.Utilities.VIBLEND_THEME.METROGREEN;
            this.btnJoinTable.Click += new System.EventHandler(this.btnJoinTable_Click);
            // 
            // btnAddTable
            // 
            this.btnAddTable.AllowAnimations = true;
            this.btnAddTable.BackColor = System.Drawing.Color.Transparent;
            this.btnAddTable.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnAddTable.Image = global::BluffinPokerClient.Properties.Resources.add;
            this.btnAddTable.Location = new System.Drawing.Point(118, 108);
            this.btnAddTable.Name = "btnAddTable";
            this.btnAddTable.RoundedCornersMask = ((byte)(15));
            this.btnAddTable.Size = new System.Drawing.Size(100, 35);
            this.btnAddTable.TabIndex = 19;
            this.btnAddTable.Text = "Add Table";
            this.btnAddTable.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.btnAddTable.UseVisualStyleBackColor = false;
            this.btnAddTable.VIBlendTheme = VIBlend.Utilities.VIBLEND_THEME.METROBLUE;
            this.btnAddTable.Click += new System.EventHandler(this.btnAddTable_Click);
            // 
            // btnRefresh
            // 
            this.btnRefresh.AllowAnimations = true;
            this.btnRefresh.BackColor = System.Drawing.Color.Transparent;
            this.btnRefresh.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnRefresh.Image = global::BluffinPokerClient.Properties.Resources.refresh;
            this.btnRefresh.Location = new System.Drawing.Point(12, 108);
            this.btnRefresh.Name = "btnRefresh";
            this.btnRefresh.RoundedCornersMask = ((byte)(15));
            this.btnRefresh.Size = new System.Drawing.Size(100, 35);
            this.btnRefresh.TabIndex = 18;
            this.btnRefresh.Text = "Refresh";
            this.btnRefresh.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageBeforeText;
            this.btnRefresh.UseVisualStyleBackColor = false;
            this.btnRefresh.VIBlendTheme = VIBlend.Utilities.VIBLEND_THEME.METROBLUE;
            this.btnRefresh.Click += new System.EventHandler(this.btnRefresh_Click);
            // 
            // btnLogOut
            // 
            this.btnLogOut.AllowAnimations = true;
            this.btnLogOut.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.btnLogOut.BackColor = System.Drawing.Color.Transparent;
            this.btnLogOut.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnLogOut.Image = global::BluffinPokerClient.Properties.Resources.exit;
            this.btnLogOut.Location = new System.Drawing.Point(365, 44);
            this.btnLogOut.Name = "btnLogOut";
            this.btnLogOut.RoundedCornersMask = ((byte)(15));
            this.btnLogOut.Size = new System.Drawing.Size(100, 58);
            this.btnLogOut.TabIndex = 17;
            this.btnLogOut.Text = "Log Out";
            this.btnLogOut.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnLogOut.UseVisualStyleBackColor = false;
            this.btnLogOut.VIBlendTheme = VIBlend.Utilities.VIBLEND_THEME.METROORANGE;
            this.btnLogOut.Click += new System.EventHandler(this.btnDisconnect_Click);
            // 
            // lblPlayerName
            // 
            this.lblPlayerName.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.lblPlayerName.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblPlayerName.Location = new System.Drawing.Point(12, 55);
            this.lblPlayerName.Name = "lblPlayerName";
            this.lblPlayerName.Size = new System.Drawing.Size(347, 35);
            this.lblPlayerName.TabIndex = 22;
            this.lblPlayerName.Text = "Player Name";
            this.lblPlayerName.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // LobbyTrainingForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.ClientSize = new System.Drawing.Size(477, 291);
            this.Controls.Add(this.lblPlayerName);
            this.Controls.Add(this.btnLeaveTable);
            this.Controls.Add(this.btnJoinTable);
            this.Controls.Add(this.btnAddTable);
            this.Controls.Add(this.btnRefresh);
            this.Controls.Add(this.btnLogOut);
            this.Controls.Add(this.pictureBox2);
            this.Controls.Add(this.pictureBox1);
            this.Controls.Add(this.tableList);
            this.Controls.Add(this.lblTitle);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MinimumSize = new System.Drawing.Size(493, 330);
            this.Name = "LobbyTrainingForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Bluffin Muffin Poker Training Client";
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.LobbyForm_FormClosed);
            this.Load += new System.EventHandler(this.LobbyTrainingForm_Load);
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Label lblTitle;
        private Com.Ericmas001.Game.BluffinMuffin.GUI.Lobby.PokerTableList tableList;
        private System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.PictureBox pictureBox2;
        private VIBlend.WinForms.Controls.vButton btnLeaveTable;
        private VIBlend.WinForms.Controls.vButton btnJoinTable;
        private VIBlend.WinForms.Controls.vButton btnAddTable;
        private VIBlend.WinForms.Controls.vButton btnRefresh;
        private VIBlend.WinForms.Controls.vButton btnLogOut;
        private System.Windows.Forms.Label lblPlayerName;

    }
}

